package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.UserService;
import com.example.pet_hospital.Service.impl.MailService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean identitySecure(String target, String Authorization){
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity=(String) claims.get("identity");
        if (target.equals(identity)){
            return true;
        }else {
            return false;
        }
    }

    public String newToken(String Authorization){
        Claims claims=JWTUtils.jwtParser(Authorization);
        String username=(String) claims.get("username");
        String user_id=(String) claims.get("user_id");
        String identity=(String) claims.get("identity");

        HashMap<String,Object> newclaim=new HashMap<>();
        newclaim.put("username",username);
        newclaim.put("user_id",user_id);
        newclaim.put("identity",identity);
        String token =JWTUtils.jwtGenerater(newclaim);
        return token;
    }

//    @PostMapping("/user/code")
//    public result sendCode(@RequestBody user u){
//
//    }

    @PostMapping("/user/login")
    public result login(@RequestBody user u){
        user us= userService.login(u);
        if (us!=null)
        {
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",us.getUsername());
            claims.put("user_id",us.getUser_id());
            claims.put("identity",us.getIdentity());
            String token = JWTUtils.jwtGenerater(claims);

            return result.success(token);
        }
        else {
            return result.error("用户名或密码错误");
        }
    }

    @PostMapping("/user/register")
    public result register (@RequestBody user u){
        //redis 先查，MySQL后查
        String user_id=stringRedisTemplate.opsForValue().get(u.getUser_id());
        if (user_id!=null){
            return result.error("该用户名已存在，无法注册");
        }

        Boolean j = userService.findUser(u);
        if (!j)//没找到，代表可以进行注册
        {
            userService.register(u);
            stringRedisTemplate.opsForValue().set(u.getUsername(), JSONUtil.toJsonStr(u),120, TimeUnit.MINUTES);
            return result.success();
        }
        else {
            return result.error("该用户名已存在，无法注册。");
        }
    }

    @PostMapping("/user/ban")
    public result UserBan(@RequestBody user u, @RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            result r = new result(0,"无操作权限！",null);
            return r;
        }
        userService.banUser(u);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/user/delete")
    public result delete(@RequestBody user u,@RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            return result.error("无操作权限！");
        }
        userService.deleteUser(u);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/user/changeinfo")
    public result alterUserInfo(@RequestBody user u, @RequestHeader String Authorization){
        userService.alterUserInfo(u);
        return result.success(newToken(Authorization));
    }

    @PostMapping("/user/getinfo")
    public result getUser(@RequestBody user u){
        if (userService.getUser(u)!=null){
            return result.success(userService.getUser(u));
        }
        else {
            return result.error("未查找到该用户。");
        }
    }

}

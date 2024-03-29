package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.UserService;
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

    public Map<String,Object> CreateNewClaims (user u){
        Map<String,Object> claims=new HashMap<>();

        claims.put("username",u.getUsername());
        claims.put("user_id",u.getUser_id());
        claims.put("identity",u.getIdentity());
        claims.put("password",u.getPassword());
        claims.put("phone_number",u.getPhone_number());
        claims.put("email",u.getEmail());
        claims.put("timestamp",u.getTimestamp());
        claims.put("token",u.getToken());
        claims.put("code",u.getCode());
        return claims;
    }

    String USER_LOGIN_KEY="LOGIN_USER:";

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
            us.setToken(token);
            stringRedisTemplate.opsForValue().
                    set(USER_LOGIN_KEY+us.getUsername(), JSONUtil.toJsonStr(us),
                            30, TimeUnit.MINUTES);
            return result.success(token);
        }
        else {
            return result.error("用户名或密码错误");
        }
    }

    @PostMapping("/user/register")
    public result register (@RequestBody user u){
        Boolean j = userService.findUser(u);
        if (!j)//没找到，代表可以进行注册
        {
            userService.register(u);
            user us=userService.getUserByName(u);
            return result.success();
        }
        else {
            return result.error("该用户名已存在，无法注册。");
        }
    }

    @PostMapping("/user/ban")
    public result UserBan(@RequestBody user u, @RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY+u.getUsername())!=null){
            stringRedisTemplate.delete(USER_LOGIN_KEY+u.getUsername());
        }
        if (userService.getUserByName(u)==null){
            return result.error("用户不存在！");
        }
        userService.banUser(u);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/delete")
    public result delete(@RequestBody user u,@RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            return result.error("无操作权限！");
        }
        userService.deleteUser(u);
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY+u.getUsername())!=null){
            stringRedisTemplate.delete(USER_LOGIN_KEY+u.getUsername());
        }
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/changeinfo")
    public result alterUserInfo(@RequestBody user u, @RequestHeader String Authorization){
        if (userService.getUserByID(u)==null){
            return result.error("用户不存在！");
        }

        userService.alterUserInfo(u);
        user us=userService.getUserByName(u);

        String token =newToken(Authorization);
        us.setToken(token);
        stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY+us.getUsername(),
                JSONUtil.toJsonStr(us),30,TimeUnit.MINUTES);
        return result.success(newToken(Authorization));

    }

    @PostMapping("/user/getinfo")
    public result getUser(@RequestBody user u){
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY+u.getUsername())!=null){
            return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(USER_LOGIN_KEY+u.getUsername()),user.class));
        }
        if (userService.getUserByName(u)!=null){
            return result.success(userService.getUserByName(u));
        }
        else {
            return result.error("未查找到该用户。");
        }
    }

    @PostMapping("/user/updatePwd")
    public result ChangePassword(@RequestBody user u, @RequestHeader String Authorization){
        if (userService.getUserByID(u)==null){
            return result.error("用户不存在！");
        }
        userService.ChangePassword(u);
        user us=userService.getUserByID(u);
        stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY+us.getUsername(),
                JSONUtil.toJsonStr(us),30, TimeUnit.MINUTES);
        if (JWTUtils.refreshTokenNeeded(Authorization)){
            return result.success(newToken(Authorization));
        }
        else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/verifyOldPwd")
    public result CheckPassword(@RequestBody user u,@RequestHeader String Authorization){
        if (userService.getUserByID(u)==null){
            return result.error("用户不存在！");
        }

        user us=userService.getUserByID(u);
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY+us.getUsername())!=null){//缓存命中
            us= JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(USER_LOGIN_KEY+us.getUsername()), user.class);
            if (!u.getOldPassword().equals(us.getPassword())){
                return result.error("密码错误！");
            }else {
                if (JWTUtils.refreshTokenNeeded(Authorization)){
                    return result.success(newToken(Authorization));
                }
                else {
                    return result.success(Authorization);
                }
            }
        }

        if (!u.getOldPassword().equals(us.getPassword())){
            return result.error("密码错误！");
        }else {
            if (JWTUtils.refreshTokenNeeded(Authorization)){
                return result.success(newToken(Authorization));
            }
            else {
                return result.success(Authorization);
            }
        }
    }

}

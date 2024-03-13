package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.UserService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static String signKey = "stargazing0115";
    @Autowired
    private UserService userService;

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

            result r=new result(1,"登陆成功",new HashMap());
            r.getData().put("Token",token);
            return r;
        }
        else {
            result r=new result(0,"用户名或密码错误！",null);
            return r;
        }
    }

    @PostMapping("/user/register")
    public result register (@RequestBody user u){
        Boolean j = userService.findUser(u);
        if (!j)//没找到，代表可以进行注册
        {
            userService.register(u);
            result r=new result(1,"注册成功",null);
            return r;
        }
        else {
            result r=new result(0,"该用户名已存在，无法注册",null);
            return r;
        }
    }

    @PostMapping("/user/ban")
    public result UserBan(@RequestBody user u, @RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            result r = new result(0,"无操作权限！",null);
            return r;
        }
        userService.banUser(u);
        result r= new result(1,"账号已禁用！",new HashMap());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/user/delete")
    public result delete(@RequestBody user u,@RequestHeader String Authorization){
        if (!identitySecure("administrator",Authorization)){
            result r= new result(0,"无操作权限！",null);
            return r;
        }
        userService.deleteUser(u);
        result r= new result(1,"用户已注销！",new HashMap());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/user/changeinfo")
    public result alterUserInfo(@RequestBody user u, @RequestHeader String Authorization){
        userService.alterUserInfo(u);
        result r= new result(1,"信息修改已完成！",new HashMap());
        r.getData().put("Token",newToken(Authorization));
        return r;
    }

    @PostMapping("/user/getinfo")
    public result getUser(@RequestBody user u){
        Map m=new HashMap();
        m.put("info",userService.getUser(u));
        if (userService.getUser(u)!=null){
            result r=new result(1,"信息已返回！",m);
            return r;
        }
        else {
            result r=new result(0,"未查找到该用户",null);
            return r;
        }
    }

}

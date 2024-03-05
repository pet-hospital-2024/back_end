package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.UserService;
import com.example.pet_hospital.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public result login(@RequestBody user u){
        user us= userService.login(u);
        if (us!=null)
        {
            String time=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")).toString();
            us.setTimestamp(time);
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",u.getUsername());
            claims.put("password",u.getPassword());
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
            user newuser=u;
            newuser.setUser_id(UUID.randomUUID().toString());
            userService.register(newuser);
            result r=new result(1,"注册成功",new HashMap());

            Map <String,Object> claims=new HashMap<>();
            claims.put("username",u.getUsername());
            claims.put("password",u.getPassword());
            String token= JWTUtils.jwtGenerater(claims);
            r.getData().put("Token",token);
            return r;
        }
        else {
            result r=new result(0,"该用户名已存在，无法注册",null);
            return r;
        }
    }

    @PostMapping("/user/ban")
    public result UserBan(@RequestBody user u){
        userService.banUser(u);
        result r= new result(1,"账号已禁用！",new HashMap());
        return r;
    }
    @PostMapping("/user/delete")
    public result delete(@RequestBody user u){
        userService.deleteUser(u);
        result r= new result(1,"用户已注销！",new HashMap());
        return r;
    }

    @PostMapping("/user/changeinfo")
    public result alterUserInfo(@RequestBody user u){
        userService.alterUserInfo(u);
        result r= new result(1,"信息修改已完成！",new HashMap());
        Map <String,Object> claims=new HashMap<>();
        claims.put("username",u.getUsername());
        claims.put("password",u.getPassword());
        String token= JWTUtils.jwtGenerater(claims);
        r.getData().put("Token",token);
        return r;
    }

}

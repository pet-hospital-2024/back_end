package com.example.pet_hospital.Controller;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.UserService;
import com.example.pet_hospital.Util.JWTUtils;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean identitySecure(String target, String Authorization) {
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity = (String) claims.get("identity");
        return target.equals(identity);
    }

    public String newToken(String Authorization) {
        Claims claims = JWTUtils.jwtParser(Authorization);
        String username = (String) claims.get("username");
        String user_id = (String) claims.get("user_id");
        String identity = (String) claims.get("identity");

        HashMap<String, Object> newclaim = new HashMap<>();
        newclaim.put("username", username);
        newclaim.put("user_id", user_id);
        newclaim.put("identity", identity);
        return JWTUtils.jwtGenerater(newclaim);
    }

    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    String USER_LOGIN_KEY = "LOGIN_USER:";

//    @PostMapping("/user/code")
//    public result sendCode(@RequestBody user u){
//
//    }

    @PostMapping("/user/login")
    public result login(@RequestBody user u) {
        user us = userService.login(u);
        if (us != null && !us.getIdentity().equals("banned")) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", us.getUsername());
            claims.put("user_id", us.getUser_id());
            claims.put("identity", us.getIdentity());
            String token = JWTUtils.jwtGenerater(claims);
            us.setToken(token);
            stringRedisTemplate.opsForValue().
                    set(USER_LOGIN_KEY + us.getUsername(), JSONUtil.toJsonStr(us),
                            30, TimeUnit.MINUTES);
            return result.success("登录成功", token);
        } else {
            if (us == null) {
                return result.error("用户名或密码错误");
            }
            return result.error("该用户已被封禁");
        }
    }

    @PostMapping("/user/register")
    public result register(@RequestBody user u) {
        //BitMapBloomFilter filter=new BitMapBloomFilter(10);
        Boolean j = userService.findUser(u);
        if (!j)//没找到，代表可以进行注册
        {
            String pwd = u.getPassword();
            String name = u.getUsername();
            if (name.length() < 4 || name.length() > 16) {
                return result.error("用户名长度不符合要求");
            }
            for (int i = 0; i < name.length(); i++) {
                if (!Character.isLetterOrDigit(name.charAt(i))) {
                    return result.error("用户名仅可包含字母和数字。");
                }
            }
            if (pwd.length() < 6 || pwd.length() > 15) {
                return result.error("密码长度不符合要求");
            }
            if (!pwd.matches(".*[a-zA-Z]+.*") || !pwd.matches(".*[0-9]+.*")) {
                return result.error("密码必须包含字母和数字。");
            }
            if (!checkEmail(u.getEmail())) {
                return result.error("邮箱格式错误。");
            }
            String regex = "(13[0-9]|15[012356789]|18[056789])\\d{8}";

            if (!u.getPhone_number().matches(regex)) {
                return result.error("手机号无效！");
            }
            userService.register(u);
            return result.success();
        } else {
            return result.error("该用户名已存在，无法注册。");
        }
    }

    @PostMapping("/user/ban")
    public result UserBan(@RequestBody user u, @RequestHeader String Authorization) {
        if (!identitySecure("administrator", Authorization)) {
            return result.error("无操作权限！");
        }
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY + u.getUsername()) != null) {
            stringRedisTemplate.delete(USER_LOGIN_KEY + u.getUsername());
        }
        if (userService.getUserByName(u) == null) {
            return result.error("用户不存在！");
        }
        userService.banUser(u);
        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/delete")
    public result delete(@RequestBody user u, @RequestHeader String Authorization) {
        if (!identitySecure("administrator", Authorization)) {
            return result.error("无操作权限！");
        }
        userService.deleteUser(u);
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY + u.getUsername()) != null) {
            stringRedisTemplate.delete(USER_LOGIN_KEY + u.getUsername());
        }
        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/changeinfo")
    public result alterUserInfo(@RequestBody user u, @RequestHeader String Authorization) {
        if (userService.getUserByID(u) == null) {
            return result.error("用户不存在！");
        }

        userService.alterUserInfo(u);
        user us = userService.getUserByName(u);

        String token = newToken(Authorization);
        us.setToken(token);
        stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY + us.getUsername(),
                JSONUtil.toJsonStr(us), 30, TimeUnit.MINUTES);
        return result.success(newToken(Authorization));

    }

    @GetMapping("/user/getinfo")
    public result getUser(@RequestParam(name = "username") String username, @RequestHeader String Authorization) {
        if (!identitySecure("administrator", Authorization)) {
            return result.error("无操作权限！");
        }

        user u = new user();
        u.setUsername(username);
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY + u.getUsername()) != null) {
            return result.success(JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(USER_LOGIN_KEY + u.getUsername()), user.class));
        }
        if (userService.getUserByName(u) != null) {
            stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY + u.getUsername(),
                    JSONUtil.toJsonStr(userService.getUserByName(u)), 30, TimeUnit.MINUTES);
            return result.success(userService.getUserByName(u));
        } else {
            return result.error("未查找到该用户。");
        }
    }

    @GetMapping("/user/getAllUser")
    public result getAllUser(@RequestHeader String Authorization,
                             @RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "size", defaultValue = "10") int size) {
        if (!identitySecure("administrator", Authorization)) {
            return result.error("无操作权限！");
        }
        if (page <= 0 || size <= 0) {
            return result.error("参数错误！");
        }
        PageInfo<user> pageResult = userService.getAllUser(page, size);
        return result.success(pageResult);
    }


    @PostMapping("/user/updatePwd")
    public result ChangePassword(@RequestBody user u, @RequestHeader String Authorization) {
        if (userService.getUserByID(u) == null) {
            return result.error("用户不存在！");
        }
        userService.ChangePassword(u);
        user us = userService.getUserByID(u);
        stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY + us.getUsername(),
                JSONUtil.toJsonStr(us), 30, TimeUnit.MINUTES);
        if (JWTUtils.refreshTokenNeeded(Authorization)) {
            return result.success(newToken(Authorization));
        } else {
            return result.success(Authorization);
        }
    }

    @PostMapping("/user/verifyOldPwd")
    public result CheckPassword(@RequestBody user u, @RequestHeader String Authorization) {
        if (userService.getUserByID(u) == null) {
            return result.error("用户不存在！");
        }

        user us = userService.getUserByID(u);
        if (stringRedisTemplate.opsForValue().get(USER_LOGIN_KEY + us.getUsername()) != null) {//缓存命中
            us = JSONUtil.toBean(stringRedisTemplate.opsForValue().
                    get(USER_LOGIN_KEY + us.getUsername()), user.class);
            if (!u.getOldPassword().equals(us.getPassword())) {
                return result.error("密码错误！");
            } else {
                if (JWTUtils.refreshTokenNeeded(Authorization)) {
                    return result.success(newToken(Authorization));
                } else {
                    return result.success(Authorization);
                }
            }
        }

        if (!u.getOldPassword().equals(us.getPassword())) {
            return result.error("密码错误！");
        } else {
            if (JWTUtils.refreshTokenNeeded(Authorization)) {
                return result.success(newToken(Authorization));
            } else {
                return result.success(Authorization);
            }
        }
    }

    //用户模糊搜索
    @GetMapping("/user/getuserbyname")
    public result GetUserByName(@RequestParam(name = "username") String name,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                @RequestHeader String Authorization) {
        if (identitySecure("user", Authorization)) {
            return result.error("无操作权限。");
        }
        if (page <= 0 || size <= 0) {
            return result.error("参数错误。");
        }
        if (name.isEmpty()) {
            return result.error("用户名不能为空。");
        }
        if (userService.getUserByName(name, page, size).getList().isEmpty()) {
            return result.error("未找到相关用户。");
        }

        return result.success(userService.getUserByName(name, page, size));

    }
}

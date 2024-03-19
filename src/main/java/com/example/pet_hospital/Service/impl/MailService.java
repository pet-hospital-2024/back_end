package com.example.pet_hospital.Service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.pet_hospital.Mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param session
     * @return
     */
    public void sendMineMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code = RandomUtil.randomNumbers(6);
            System.out.println("随机验证码为："+code);
            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);

            mailMessage.setText("您收到的验证码是："+code);//内容

            mailMessage.setTo(email);//发给谁

            mailMessage.setFrom(from);//自己的邮箱

            mailSender.send(mailMessage);//发送
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//
//    /**
//     * 检验验证码是否一致
//     * @param userVo
//     * @param session
//     * @return
//     */
//    public boolean registered(UserVo userVo, HttpSession session){
//        //获取session中的验证信息
//        String email = (String) session.getAttribute("email");
//        String code = (String) session.getAttribute("code");
//
//        //获取表单中的提交的验证信息
//        String voCode = userVo.getCode();
//
//        //如果email数据为空，或者不一致，注册失败
//        if (email == null || email.isEmpty()){
//            //return "error,请重新注册";
//            return false;
//        }else if (!code.equals(voCode)){
//            //return "error,请重新注册";
//            return false;
//        }
//
//        //保存数据
//        User user = UserVoToUser.toUser(userVo);
//
//        //将数据写入数据库
//        userMapper.insertUser(user);
//
//        //跳转成功页面
//        return true;
//    }
//
//    /**
//     * 通过输入email查询password，然后比较两个password，如果一样，登录成功
//     * @param email
//     * @param password
//     * @return
//     */
//
//    public boolean loginIn(String email, String password){
//
//        User user = userMapper.queryByEmail(email);
//
//        if(!user.getPassword().equals(password)){
//            return false;
//        }
//        //System.out.println("登录成功:数据库密码是："+user.getPassword());
//        return true;
//    }
}

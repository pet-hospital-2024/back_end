package com.example.pet_hospital.Interceptor;

import cn.hutool.json.JSONUtil;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("preHandler运行了..................................");
        String url= request.getRequestURL().toString();
        log.info("请求的url: {}",url);

        if (url.contains("login") || url.contains("register")){
            log.info("无需鉴权操作，放行。");
            return true;
        }

        String jwt= request.getHeader("Authorization");

        if (jwt==null || jwt.isEmpty()){
            log.info("请求头为空，返回未登录界面。");
            result error =result.error("Didn't login. Please login first.");
            String notLogin= JSONUtil.toJsonStr(error);
            response.getWriter().write(notLogin);
            return false;
        }

        try{
            JWTUtils.jwtParser(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("令牌解析失败。");
            result error =result.tokenError("The token has expired or it's invalid. Please login.");
            String notLogin= JSONUtil.toJsonStr(error);
            response.getWriter().write(notLogin);
            return false;
        }
        log.info("令牌解析成功,放行。");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterHandler运行了.......");
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

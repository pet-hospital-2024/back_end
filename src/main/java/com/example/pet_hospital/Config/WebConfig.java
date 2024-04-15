package com.example.pet_hospital.Config;

import com.example.pet_hospital.Interceptor.LoginInterceptor;
import com.example.pet_hospital.Util.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    /*@Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherServlet() {
        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(new DispatcherServlet());
        registration.setAsyncSupported(true);
        return registration;
    }*/

    @Bean
    public FilterRegistrationBean<Filters> loggingFilter() {
        FilterRegistrationBean<Filters> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filters());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }


    @Bean
    public DispatcherServletPath dispatcherServletPath() {
        return () -> "/";
    }
}

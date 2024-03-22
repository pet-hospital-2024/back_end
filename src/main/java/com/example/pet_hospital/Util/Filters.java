package com.example.pet_hospital.Util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class Filters implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Filter logic here
        // For example, logging request info:
        System.out.println("Request received for: " + request.getRemoteAddr());

        // To continue the filter chain, call chain.doFilter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Filter shutdown logic here
    }
}

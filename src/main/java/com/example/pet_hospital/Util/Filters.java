package com.example.pet_hospital.Util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*",asyncSupported = true)
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
        System.out.println("Request received for: " + request.getRemoteAddr()+ "  "+ request.getLocalName());

        // To continue the filter chain, call chain.doFilter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Filter shutdown logic here
    }
}

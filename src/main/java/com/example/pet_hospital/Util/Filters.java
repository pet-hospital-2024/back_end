package com.example.pet_hospital.Util;

import jakarta.servlet.annotation.WebFilter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

@WebFilter(urlPatterns = "/*")
public class Filters implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return true;
    }
}

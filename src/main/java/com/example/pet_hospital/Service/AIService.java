package com.example.pet_hospital.Service;


import jakarta.servlet.http.HttpSession;

public interface AIService {

    public String getAnswer(String query, HttpSession session) throws Exception;

}

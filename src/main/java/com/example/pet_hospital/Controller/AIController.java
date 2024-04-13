package com.example.pet_hospital.Controller;


import com.example.pet_hospital.Entity.query;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.AIService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {


    @Autowired
    private AIService aiService;


    @PostMapping("/ai")
    public result getErnieBotTurboResponse(@RequestBody query query,
                                           HttpSession session,
                                           @RequestHeader("Authorization") String Authorization
    ) {

        //不能为空
        if (query.getQuery() == null || query.getQuery().isEmpty()) {
            return result.error("Query cannot be empty");
        }


        try {
            String answer = aiService.getAnswer(query.getQuery(), session);
            return result.success(answer);
        } catch (Exception e) {
            return result.error("Error while processing the request: " + e.getMessage());
        }

    }

    //重置ai对话
    @PostMapping("/reset")
    public result reset(HttpServletRequest request, @RequestHeader("Authorization") String Authorization) {
        HttpSession session = request.getSession(false);  // 获取当前会话，如果不存在则返回null
        if (session != null) {
            session.invalidate();  // 使当前会话失效
        }
        session = request.getSession(true);
        return result.success("会话已重置");
    }

}

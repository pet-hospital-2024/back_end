package com.example.pet_hospital.Service.impl;


import com.baidubce.appbuilder.console.agentbuilder.AgentBuilder;
import com.baidubce.appbuilder.model.agentbuilder.AgentBuilderIterator;
import com.baidubce.appbuilder.model.agentbuilder.AgentBuilderResult;
import com.example.pet_hospital.Service.AIService;
import com.example.pet_hospital.Util.AppBuilderConfig;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class AIService_impl implements AIService {

    @Autowired
    private AppBuilderConfig appBuilderConfig;

    public String getAnswer(String query, HttpSession session) throws Exception {
        String appId = appBuilderConfig.getAppId();
        String token = appBuilderConfig.getToken();
        System.setProperty("APPBUILDER_TOKEN", token);

        AgentBuilder agentBuilder = new AgentBuilder(appId);

        // 尝试从会话中获取已存在的对话ID
        String conversationId = (String) session.getAttribute("conversationId");
        if (conversationId == null) {
            // 如果会话中没有对话ID，则创建一个新的对话
            conversationId = agentBuilder.createConversation();
            session.setAttribute("conversationId", conversationId);
        }

        AgentBuilderIterator itor = agentBuilder.run(query, conversationId, null, true);
        StringBuilder answer = new StringBuilder();
        while (itor.hasNext()) {
            AgentBuilderResult response = itor.next();
            answer.append(response.getAnswer());
        }
        return answer.toString();
    }




}

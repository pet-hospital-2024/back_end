package com.example.pet_hospital.Service.impl;


import com.baidubce.appbuilder.base.exception.AppBuilderServerException;
import com.baidubce.appbuilder.console.agentbuilder.AgentBuilder;
import com.baidubce.appbuilder.model.agentbuilder.AgentBuilderIterator;
import com.baidubce.appbuilder.model.agentbuilder.AgentBuilderResult;
import com.example.pet_hospital.Service.AIService;
import com.example.pet_hospital.Util.AppBuilderConfig;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Service
@Slf4j
@Data
public class AIService_impl implements AIService {

    @Autowired
    private AppBuilderConfig appBuilderConfig;

    public Flux<ServerSentEvent<String>> getAnswer(String query, HttpSession session) throws Exception {
        String appId = appBuilderConfig.getAppId();
        String token = appBuilderConfig.getToken();
        System.setProperty("APPBUILDER_TOKEN", token);

        AgentBuilder agentBuilder = new AgentBuilder(appId);

        // 尝试从会话中获取已存在的对话ID
        final String conversationId = session.getAttribute("conversationId") != null ?
                (String) session.getAttribute("conversationId") :
                agentBuilder.createConversation();

        // 如果会话中没有对话ID，则设置一个新的对话ID
        if (session.getAttribute("conversationId") == null) {
            session.setAttribute("conversationId", conversationId);
        }


        final AgentBuilder finalAgentBuilder = agentBuilder;

        return Flux.create(sink -> {
            try {
                AgentBuilderIterator itor = finalAgentBuilder.run(query, conversationId, null, true);
                while (itor.hasNext()) {
                    AgentBuilderResult response = itor.next();
                    sink.next(ServerSentEvent.builder(response.getAnswer()).build());
                }
                sink.complete();
            } catch (IOException | AppBuilderServerException e) {
                sink.error(e);  // 发送错误信号
            }
        });
    }


        /*AgentBuilderIterator itor = agentBuilder.run(query, conversationId, null, true);
        StringBuilder answer = new StringBuilder();
        while (itor.hasNext()) {
            AgentBuilderResult response = itor.next();
            answer.append(response.getAnswer());
        }
        return answer.toString();*/


}

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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.AbstractMap;

@Service
@Slf4j
@Data
public class AIService_impl implements AIService {

    @Autowired
    private AppBuilderConfig appBuilderConfig;

    public Flux<ServerSentEvent<String>> getAnswer(String query, HttpSession session) {
        return Mono.fromCallable(() -> {
                    String appId = appBuilderConfig.getAppId();
                    String token = appBuilderConfig.getToken();
                    System.setProperty("APPBUILDER_TOKEN", token);

                    AgentBuilder agentBuilder = new AgentBuilder(appId);
                    // 尝试从会话中获取已存在的对话ID
                    String conversationId = (String) session.getAttribute("conversationId");
                    if (conversationId == null) {
                        conversationId = agentBuilder.createConversation();
                        session.setAttribute("conversationId", conversationId);
                    }
                    return new AbstractMap.SimpleEntry<>(agentBuilder, conversationId);
                })
                .subscribeOn(Schedulers.boundedElastic()) // 在支持阻塞操作的调度器上执行
                .flatMapMany(entry -> {
                    AgentBuilder agentBuilder = entry.getKey();
                    String conversationId = entry.getValue();
                    return Flux.create(sink -> {
                        try {
                            AgentBuilderIterator itor = agentBuilder.run(query, conversationId, null, true);
                            while (itor.hasNext()) {
                                AgentBuilderResult response = itor.next();
                                sink.next(ServerSentEvent.builder(response.getAnswer()).build());
                            }
                            sink.complete();
                        } catch (IOException | AppBuilderServerException e) {
                            sink.error(e);  // 发送错误信号
                        }
                    });
                });
    }
}

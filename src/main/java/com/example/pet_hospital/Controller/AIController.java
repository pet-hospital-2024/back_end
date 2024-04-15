package com.example.pet_hospital.Controller;


import com.example.pet_hospital.Entity.query;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.AIService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AIController {


    @Autowired
    private AIService aiService;


    @PostMapping(value = "/ai", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getErnieBotTurboResponse(@RequestBody query query,
                                                                  HttpSession session,
                                                                  @RequestHeader("Authorization") String Authorization) throws Exception {
        if (query.getQuery() == null || query.getQuery().isEmpty()) {
            return Flux.error(new IllegalStateException("Query cannot be empty"));
        }
        return aiService.getAnswer(query.getQuery(), session);
    }

    //重置ai对话
    @PostMapping("/reset")
    public result reset(HttpServletRequest request, @RequestHeader("Authorization") String Authorization) {
        HttpSession session = request.getSession(false);  // 获取当前会话，如果不存在则返回null
        if (session != null) {
            session.invalidate();  // 使当前会话失效
        }
        request.getSession(true);
        return result.success("会话已重置");
    }

}

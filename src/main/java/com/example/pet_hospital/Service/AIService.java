package com.example.pet_hospital.Service;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface AIService {

    Flux<ServerSentEvent<String>> getAnswer(String query, HttpSession session) throws Exception;

}

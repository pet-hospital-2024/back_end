package com.example.pet_hospital.Controller;

import com.example.pet_hospital.Entity.ErnieBotTurboResponse;
import com.example.pet_hospital.Entity.ErnieBotTurboStreamParam;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {


    @Autowired
    private  AIService aiService;


    @PostMapping("/ai")
    public result getErnieBotTurboResponse(@RequestBody ErnieBotTurboStreamParam param,
                                           @RequestHeader("Authorization") String token
                                            ) {
        ErnieBotTurboResponse response = aiService.ernieBotTurbo(param);
        return result.success(ResponseEntity.ok(response));
    }

}

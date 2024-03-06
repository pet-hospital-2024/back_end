package com.example.pet_hospital.Controller;
import com.example.pet_hospital.Entity.result;
import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.Service.UserService;
import com.example.pet_hospital.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class diseaseController {
    @Autowired
    private DiseaseService diseaseService;


}

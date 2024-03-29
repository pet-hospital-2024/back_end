package com.example.pet_hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.example.pet_hospital.Mapper")
public class PetHospitalApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetHospitalApplication.class, args);
	}

}

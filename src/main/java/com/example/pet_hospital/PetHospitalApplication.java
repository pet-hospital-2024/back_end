package com.example.pet_hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PetHospitalApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetHospitalApplication.class, args);
	}

}

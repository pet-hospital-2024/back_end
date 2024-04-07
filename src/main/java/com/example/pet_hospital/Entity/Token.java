package com.example.pet_hospital.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    private String access_token;
    private Integer expires_in;
    private String error;
    private String error_description;
}


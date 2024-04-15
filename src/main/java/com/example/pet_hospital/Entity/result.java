package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class result {
    private Integer code;
    private String message;
    private Object data;


    public static result success(Object data){
        return new result(1,"success",data);
    }

    public static result success(){
        return new result(1,"success",null);
    }

    public static result success(String msg, Object data){
        return new result(1,msg,data);
    }

    public static result error (String message){
        return new result(0,message,null);
    }

    public static result tokenError(String message){
        return new result(-1,message,null);
    }

    public static result nonComplete (Object data){
        return new result(-2,"缺失分片",data);
    }
}

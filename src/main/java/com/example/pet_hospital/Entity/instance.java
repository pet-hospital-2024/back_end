package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class instance {
    int id;
    String name;
    int kind;
    String text;
    String result;
    String treatment;
    String medicine;
    String price;
    String intro;
    instance dis;
}

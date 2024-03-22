package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class instance {
    int instance_id;
    String name;
    String text;
    String result;
    String treatment;
    String medicine;
    String price;
    String intro;
    String dis_id;
    String kind_id;
}

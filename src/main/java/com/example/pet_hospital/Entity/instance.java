package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class instance {
    String uuid;
    String name;
    String text;
    String result;
    String treatment;
    String medicine;
    String price;
    String intro;
    String dis_id;
    String kind_id;

    public String getUuid() {
        return uuid;
    }

    public String getDis_id() {
        return dis_id;
    }

    public String getName() {
        return name;
    }
}

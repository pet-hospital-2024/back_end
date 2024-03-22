package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class disease {
    String dis_id;//疾病id
    String kind_id;//科室id
    String name;

    public String getKind_id() {
        return kind_id;
    }

    public String getDis_id() {
        return dis_id;
    }

    public String getName(){
        return name;
    }
}

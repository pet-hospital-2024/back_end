package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class kind {
    String kind_id;
    String name;

    public String getKind_id() {
        return kind_id;
    }
    public String getName() {
        return name;
    }
}

package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class kind {
    String id;
    String name;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}

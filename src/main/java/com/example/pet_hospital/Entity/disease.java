package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class disease {
    String disease_id;//疾病id
    String department_id;//科室id
    String disease_name;


}

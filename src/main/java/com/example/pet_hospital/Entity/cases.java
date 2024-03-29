package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cases {

    String case_id;
    String case_name;
    String case_examination;
    String case_result;
    String case_treatment;
    String case_medicine;
    String case_cost;
    String case_introduction;
    String disease_id;
    String department_id;


}

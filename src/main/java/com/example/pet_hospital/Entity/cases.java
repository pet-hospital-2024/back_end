package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    String disease_name;
    String department_id;
    String department_name;
    MultipartFile file;
    String media_id;
    String media_url;
    String media_name;
    String media_type;
    String category;
    String created_at;
    String updated_at;


}

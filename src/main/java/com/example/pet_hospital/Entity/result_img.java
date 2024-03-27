package com.example.pet_hospital.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class result_img {

    String case_resultimg_id;
    String case_id;
    String case_resultimg_url;
    String case_resultimg_name;
}

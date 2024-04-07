package com.example.pet_hospital.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paper {
    String paper_id;
    String paper_name;
    Integer duration;
    String question_id;
    Integer question_number;
    Integer value;
    Integer question_value;
    Integer question_order;
    List<question> questions;
}

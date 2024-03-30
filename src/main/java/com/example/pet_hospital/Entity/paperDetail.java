package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paperDetail {
    String paper_id;
    String paper_name;
    int duration;
    Integer question_number;
    Integer value;
    List<paper_question> questions;
}

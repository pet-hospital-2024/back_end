package com.example.pet_hospital.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class examList {
    String exam_id;
    String paper_id;
    String exam_start;
    String exam_end;
    String exam_name;
    int duration;
}

package com.example.pet_hospital.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class question {
    String question_id;
    String question_body;
    String type;
    String right_choice;
    String A;
    String B;
    String C;
    String D;
    String judgement;
    Integer value;
    Integer order;
    List<Map<String, String>> options;
    String disease_id;//疾病id
    String department_id;//所属科室id
}

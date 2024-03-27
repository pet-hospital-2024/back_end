package com.example.pet_hospital.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    String disease_id;//疾病id
    String department_id;//所属科室id
}

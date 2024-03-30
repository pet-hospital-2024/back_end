package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paper_question {
    String question_id;
    int order;
    String question_body;
    String type;
    String a;
    String b;
    String c;
    String d;
    String right_choice;
    String judgement;
    int value;
}

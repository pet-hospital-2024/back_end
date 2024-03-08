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
    String disease_kind;
    String right_choice;
    String A;
    String B;
    String C;
    String D;
    String judgement;

    public String getId() {
        return question_id;
    }
}

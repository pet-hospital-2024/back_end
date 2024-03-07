package com.example.pet_hospital.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

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

    public Integer getId() {
        return Integer.parseInt(question_id);
    }
}

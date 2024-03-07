package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.question;

public interface PracticeService {
    question addQuestion(question q);
    void deleteQuestion(question q);
    void alterQuestion(question q);
    question[] getAllQuestions();
}

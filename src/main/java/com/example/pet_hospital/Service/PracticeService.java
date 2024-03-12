package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;

public interface PracticeService {
    void addQuestion(question q);
    void deleteQuestion(question q);
    void alterQuestion(question q);
    question[] getAllQuestions();

    question getQuestion(question q);

    void createNewPaper(paper p);

    void insertNewQuestion(paper p);
}

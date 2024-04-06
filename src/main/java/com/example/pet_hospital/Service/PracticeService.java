package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.question;
import com.github.pagehelper.PageInfo;

public interface PracticeService {
    void addQuestion(question q);

    void deleteQuestion(question q);

    void alterQuestion(question q);

    PageInfo<question> getAllQuestions(int page, int size);

    question getQuestionByID(question q);

    public question getQuestionByBody(question q);

    String getDiseaseID(question q);

    String getDepartmentID(question q);

    PageInfo<question> getquestionbyname(String name, int page, int size);

    PageInfo<question> getquestionbydisease(String name, int page, int size);
}

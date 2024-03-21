package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Mapper.PracticeMapper;
import com.example.pet_hospital.Service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeService_impl implements PracticeService {
    @Autowired
    private PracticeMapper practiceMapper;

    @Override
    public void addQuestion(question q) {
        practiceMapper.addQuestion(q);
    }

    @Override
    public void deleteQuestion(question q) {
        practiceMapper.deleteQuestion(q);
    }

    @Override
    public void alterQuestion(question q) {
        practiceMapper.alterQuestion(q);
    }

    @Override
    public question[] getAllQuestions() {
        return practiceMapper.getAllQuestions();
    }

    @Override
    public question getQuestionByID(question q) {
        return practiceMapper.getQuestionByID(q);
    }

    @Override
    public question getQuestionByBody(question q) {
        return practiceMapper.getQuestionByBody(q);
    }

    @Override
    public void createNewPaper(paper p) {
        //p.setQuestionsJSON(new JSONArray(p.getQuestions()).toString());
        practiceMapper.createNewPaper(p);
    }

    @Override
    public paper getPaper(paper p) {
        return practiceMapper.getPaper(p);
    }

    @Override
    public void insertNewQuestion(paper p) {
        practiceMapper.insertNewQuestion(p);
    }
}

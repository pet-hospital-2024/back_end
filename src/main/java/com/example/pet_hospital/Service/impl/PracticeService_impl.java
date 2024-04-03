package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Mapper.PracticeMapper;
import com.example.pet_hospital.Service.PracticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageInfo<question> getAllQuestions(int page, int size) {
        PageHelper.startPage(page, size);
        List<question> questions = practiceMapper.getAllQuestions();
        PageInfo<question> pageInfo = new PageInfo<>(questions);
        return pageInfo;
    }

    @Override
    public question getQuestionByID(question q) {
        return practiceMapper.getQuestionByID(q);
    }

    @Override
    public question getQuestionByBody(question q) {
        return practiceMapper.getQuestionByBody(q);
    }


}

package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.Exam;
import com.example.pet_hospital.Mapper.ExamMapper;
import com.example.pet_hospital.Service.ExamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExamService_impl implements ExamService {

    @Autowired
    ExamMapper examMapper;
    @Override
    public void createExam(Exam exam) {
        examMapper.addExam(exam);
    }

    @Override
    public void updateExam(Exam exam) {
        examMapper.updateExam(exam);
    }

    @Override
    public void deleteExam(Exam exam) {
        examMapper.deleteExam(exam);
    }

    @Override
    public Exam getExamById(Exam exam) {
        return examMapper.getExamById(exam);
    }

    @Override
    public Exam getExamByName(Exam exam) {
        return examMapper.getExamByName(exam);
    }

    @Override
    public ArrayList<Exam> getExamList() {
        return examMapper.getExamList();
    }

    @Override
    public PageInfo<Exam> getAllExam(int page, int size) {
        PageHelper.startPage(page, size);
        ArrayList<Exam> examList = examMapper.getExamList();
        return new PageInfo<>(examList);
    }

}

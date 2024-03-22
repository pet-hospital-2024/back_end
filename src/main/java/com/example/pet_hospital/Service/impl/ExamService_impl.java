package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.Exam;
import com.example.pet_hospital.Mapper.ExamMapper;
import com.example.pet_hospital.Service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}

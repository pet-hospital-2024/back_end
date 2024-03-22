package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.Exam;

public interface ExamService {

    void createExam(Exam exam);

    void updateExam(Exam exam);


    void deleteExam(Exam exam);


    Exam getExamById(Exam exam);
}

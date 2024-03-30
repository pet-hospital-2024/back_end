package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.Exam;
import com.example.pet_hospital.Entity.examList;

import java.util.ArrayList;

public interface ExamService {

    void createExam(Exam exam);

    void updateExam(Exam exam);


    void deleteExam(Exam exam);

    Exam getExamById(Exam exam);

    ArrayList<examList> getExamList();
}

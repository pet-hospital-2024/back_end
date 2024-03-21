package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import org.springframework.stereotype.Service;


public interface PaperService {
    void createNewPaper(paper p);

    paper getPaperByID(paper p);
    void insertNewQuestion(paper p);

    void deletePaper(paper p);

    void deleteQuestionFromPaper(paper p);

    question[] getQuestionsFromPaper(paper p);
}

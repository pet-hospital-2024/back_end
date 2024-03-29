package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.paper;

import java.util.ArrayList;


public interface PaperService {
    void createNewPaper(paper p);

    paper getPaperByID(paper p);

    paper getPaperByName(paper p);

    ArrayList<paper> getPaperList ();

    void insertNewQuestion(paper p);

    void deletePaper(paper p);

    void deleteQuestionFromPaper(paper p);

    ArrayList<String> getQuestionsFromPaper(paper p);
}

package com.example.pet_hospital.Service;

import com.example.pet_hospital.Dto.option;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Vo.paperDetail;
import com.example.pet_hospital.Dto.questonifexist;

import java.util.ArrayList;


public interface PaperService {
    void createNewPaper(paper p);

    paper getPaperByID(paper p);

    paper getPaperByName(paper p);

    ArrayList<paper> getPaperList ();

    void insertNewQuestion(paper p);

    void deletePaper(paper p);

    void deleteQuestionFromPaper(paper p);

    paperDetail getQuestionsFromPaper(String paper_id);



    questonifexist ifPaperContainsQueston(paper p);

    option selectOptionsForQuestion(String question_id);

    questonifexist ifOrderExist(paper p);

    void updatePaperValueAndQuestionNumber(String paperId, int value, int questionNumber);
}

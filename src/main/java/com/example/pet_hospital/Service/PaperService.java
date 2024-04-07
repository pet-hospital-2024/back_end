package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.paper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface PaperService {
    void createNewPaper(paper p);

    paper getPaperByID(paper p);

    paper getPaperByName(paper p);

    PageInfo<paper> getPaperList (int page, int size);

    void insertNewQuestion(paper p);

    void deletePaper(paper p);

    void deleteQuestionFromPaper(paper p);

    paper getQuestionsFromPaper(String paper_id);



    List<Map<String,String>> ifPaperContainsQueston(paper p);

    List<Map<String, String>> selectOptionsForQuestion(String question_id);

    List<Map<String,String>> ifOrderExist(paper p);

    void updatePaperValueAndQuestionNumber(String paperId, int value, int questionNumber);

    void changePaper(paper p);

    List<String> getQuestionsIDFromPaper(String paperId);
}

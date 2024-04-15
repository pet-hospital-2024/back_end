package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Mapper.PaperMapper;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperService_impl implements PaperService {

    public Boolean identitySecure(String target, String Authorization){
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity=(String) claims.get("identity");
        return target.equals(identity);
    }

    public String newToken(String Authorization){
        return JWTUtils.newToken(Authorization);
    }

    @Autowired
    private PaperMapper paperMapper;
    @Override
    public void createNewPaper(paper p) {
        paperMapper.createNewPaper(p);
    }

    @Override
    public paper getPaperByID(paper p) {
        return paperMapper.getPaperByID(p);
    }

    @Override
    public paper getPaperByName(paper p) {
        return paperMapper.getPaperByName(p);
    }

    @Override
    public PageInfo<paper> getPaperList(int page, int size) {
        PageHelper.startPage(page, size);
        List<paper> papers = paperMapper.getPaperList();
        return new PageInfo<>(papers);
    }

    @Override
    public void insertNewQuestion(paper p) {
        paperMapper.insertNewQuestion(p);
    }

    @Override
    public void deletePaper(paper p) {
        paperMapper.deletePaperFromPapers(p);
        paperMapper.deletePaperFromPaper_Questions(p);
    }

    @Override
    public void deleteQuestionFromPaper(paper p) {
        paperMapper.deleteQuestionFromPaper_Questions(p);
    }




    @Override
    public List<Map<String,String>> ifPaperContainsQueston(paper p) {
        return paperMapper.ifPaperContainsQueston(p);
    }

    @Override
    public List<Map<String, String>> selectOptionsForQuestion(String question_id) {


        Map<String, String> simpleMap=paperMapper.selectOptionsForQuestion(question_id);
        List<Map<String, String>> complexList = new ArrayList<>();

        for (Map.Entry<String, String> entry : simpleMap.entrySet()) {
            Map<String, String> option = new HashMap<>();
            option.put("optCode", entry.getKey());
            option.put("optContents", entry.getValue());
            complexList.add(option);
        }

        return complexList;

    }


    @Override
    public paper getQuestionsFromPaper(String paper_id) {

        return paperMapper.getQuestionsFromPaper(paper_id);
    }

    @Override
    public List<Map<String,String>> ifOrderExist(paper p) {
        return paperMapper.ifOrderExist(p);
    }

    @Override
    public void updatePaperValueAndQuestionNumber(String paperId, int value, int questionNumber) {
        paperMapper.updatePaperValueAndQuestionNumber(paperId, value, questionNumber);
    }

    @Override
    public void changePaper(paper p) {
        paperMapper.changePaper(p);
    }

    @Override
    public List<String> getQuestionsIDFromPaper(String paperId) {
        return paperMapper.getQuestionsIDFromPaper(paperId);
    }
}

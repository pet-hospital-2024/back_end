package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.option;
import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.paperDetail;
import com.example.pet_hospital.Entity.questonifexist;
import com.example.pet_hospital.Mapper.PaperMapper;
import com.example.pet_hospital.Service.PaperService;
import com.example.pet_hospital.Util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PaperService_impl implements PaperService {

    public Boolean identitySecure(String target, String Authorization){
        Claims claims = JWTUtils.jwtParser(Authorization);
        String identity=(String) claims.get("identity");
        if (target.equals(identity)){
            return true;
        }else {
            return false;
        }
    }

    public String newToken(String Authorization){
        Claims claims=JWTUtils.jwtParser(Authorization);
        String username=(String) claims.get("username");
        String user_id=(String) claims.get("user_id");
        String identity=(String) claims.get("identity");

        HashMap<String,Object> newclaim=new HashMap<>();
        newclaim.put("username",username);
        newclaim.put("user_id",user_id);
        newclaim.put("identity",identity);
        String token =JWTUtils.jwtGenerater(newclaim);
        return token;
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
    public ArrayList<paper> getPaperList() {
        return paperMapper.getPaperList();
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
    public paperDetail getQuestionsFromPaper(String paper_id) {
        return paperMapper.getQuestionsFromPaper(paper_id);
    }

    @Override
    public void updatePaper(paper p) {
        paperMapper.updatePaper(p);
    }

    @Override
    public questonifexist ifPaperContainsQueston(paper p) {
        return paperMapper.ifPaperContainsQueston(p);
    }

    @Override
    public option selectOptionsForQuestion(String question_id) {
        return paperMapper.selectOptionsForQuestion(question_id);
    }
}

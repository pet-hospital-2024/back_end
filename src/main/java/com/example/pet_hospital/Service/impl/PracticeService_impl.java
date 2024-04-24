package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.question;
import com.example.pet_hospital.Mapper.PaperMapper;
import com.example.pet_hospital.Mapper.PracticeMapper;
import com.example.pet_hospital.Service.PracticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeService_impl implements PracticeService {
    @Autowired
    private PracticeMapper practiceMapper;

    private PaperMapper paperMapper;

    @Override
    public void addQuestion(question q) {
        practiceMapper.addQuestion(q);
    }

    @Override
    public void deleteQuestion(question q) {
        //先获取有哪些试卷包含了这个问题
        List<String> paperIDs = practiceMapper.getPaperIDsByQuestionID(q);

        //获取问题的order
        int dOrder = practiceMapper.getQuestionByID(q).getOrder();

        //删除试卷中的问题
        practiceMapper.deletePaperQuestion(q);
        //遍历试卷list，每张试卷中的order大于当前order的试题的order-1
        for (String paperID : paperIDs) {
            //获取试卷中的问题
            List<question> questions=paperMapper.getQuestionsFromPaper(paperID).getQuestions();
            for (question qq:questions){
                if (qq.getOrder()>dOrder){
                    paperMapper.changePaperQuestion(paperID,qq.getQuestion_id(),(qq.getOrder()-1));
                }
            }
        }


        //删除问题
        practiceMapper.deleteQuestion(q);
    }

    @Override
    public void alterQuestion(question q) {
        practiceMapper.alterQuestion(q);
    }

    @Override
    public PageInfo<question> getAllQuestions(int page, int size) {
        PageHelper.startPage(page, size);
        List<question> questions = practiceMapper.getAllQuestions();
        return new PageInfo<>(questions);
    }

    @Override
    public question getQuestionByID(question q) {
        return practiceMapper.getQuestionByID(q);
    }

    @Override
    public question getQuestionByBody(question q) {
        return practiceMapper.getQuestionByBody(q);
    }

    @Override
    public String getDiseaseID(question q) {
        return practiceMapper.getDiseaseID(q);
    }//根据疾病name获取id，根据科室name获取id

    @Override
    public String getDepartmentID(question q) {
        return practiceMapper.getDepartmentID(q);
    }

    @Override
    public PageInfo<question> getquestionbyname(String name, int page, int size) {
        PageHelper.startPage(page, size);
        List<question> questions = practiceMapper.getQuestionByName(name);
        return new PageInfo<>(questions);
    }

    @Override
    public PageInfo<question> getquestionbydisease(String name, int page, int size) {
        PageHelper.startPage(page, size);
        List<question> questions = practiceMapper.getQuestionByDisease(name);
        return new PageInfo<>(questions);
    }

    @Override
    public boolean questionUsed(question q) {
        return practiceMapper.questionUsed(q) > 0;
    }


}

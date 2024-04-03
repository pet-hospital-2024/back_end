package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PracticeMapper {
    @Delete("delete from syf.questions where question_id=#{question_id}")
    void deleteQuestion(question q);

    @Insert("insert into syf.questions values(uuid_short(),#{type},#{question_body},#{A},#{B},#{C},#{D},#{right_choice},#{judgement},#{disease_id},#{department_id})")
    void addQuestion(question q);

    @Update("update syf.questions set  type=#{type}, question_body=#{question_body}, a=#{a}, b=#{b}, c=#{c}, d=#{d}, right_choice=#{right_choice}, judgement=#{judgement} where question_id=#{question_id}")
    void alterQuestion(question q);

    @Select("select * from syf.questions")
    List<question> getAllQuestions();

    /////////////////////////////////////
    @Select("select * from syf.questions where question_id=#{question_id}")
    question getQuestionByID(question q);

    @Select("select * from syf.questions where question_body=#{question_body}")
    question getQuestionByBody(question q);



}

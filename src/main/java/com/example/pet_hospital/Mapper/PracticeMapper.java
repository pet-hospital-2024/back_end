package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.question;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PracticeMapper {
    @Delete("delete from syf.question where question_id=#{question_id}")
    void deleteQuestion(question q);

    @Insert("insert into syf.question values(uuid_short(),#{disease_kind},#{type},#{question_body},#{A},#{B},#{C},#{D},#{right_choice},#{judgement)")
    void addQuestion(question q);

    @Update("update syf.question set disease_kind=#{disease_kind}, type=#{type}, question_body=#{question_body}, A=#{A}, B=#{B}, C=#{C}, D=#{D}, right_choice=#{right_choice}, judgement=#{judgement} where question_id=#{question_id}")
    void alterQuestion(question q);

    @Select("select * from syf.question")
    question[] getAllQuestions();
}

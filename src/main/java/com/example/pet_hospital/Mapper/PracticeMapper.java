package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PracticeMapper {
    @Delete("delete from syf.questions where question_id=#{question_id}")
    void deleteQuestion(question q);

    @Insert("insert into syf.questions values(uuid_short(),#{type},#{question_body},#{A},#{B},#{C},#{D},#{right_choice},#{judgement},#{dis_id},#{kind_id})")
    void addQuestion(question q);

    @Update("update syf.questions set  type=#{type}, question_body=#{question_body}, a=#{a}, b=#{b}, c=#{c}, d=#{d}, right_choice=#{right_choice}, judgement=#{judgement} where question_id=#{question_id}")
    void alterQuestion(question q);

    @Select("select * from syf.questions")
    question[] getAllQuestions();

    @Select("select * from syf.questions where question_id=#{question_id}")
    question getQuestionByID(question q);

    @Select("select * from syf.questions where question_body=#{question_body}")
    question getQuestionByBody(question q);

    @Insert("insert into syf.papers values (uuid_short(),#{duration},#{paper_name},null,null)")
    void createNewPaper(paper p);

    @Insert("insert into syf.papers values (#{paper_id},#{duration},#{paper_name},#{question_number},#{value})")
    void insertNewQuestion(paper p);

    @Select("select * from syf.papers where paper_id=#{paper_id}")
    paper getPaper (paper p);

}

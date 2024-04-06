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


    //返回的属性增加疾病和科室的name，需要question和disease表和department表的连接查询，通过disease_id和department_id
    @Select("select * from syf.questions join syf.disease on syf.questions.disease_id=syf.disease.disease_id join syf.department on syf.questions.department_id=syf.department.department_id")
    @Results({
            @Result(property = "question_id", column = "question_id"),
            @Result(property = "question_body", column = "question_body"),
            @Result(property = "type", column = "type"),
            @Result(property = "right_choice", column = "right_choice"),
            @Result(property = "A", column = "A"),
            @Result(property = "B", column = "B"),
            @Result(property = "C", column = "C"),
            @Result(property = "D", column = "D"),
            @Result(property = "judgement", column = "judgement"),
            @Result(property = "disease_id", column = "disease_id"),
            @Result(property = "department_id", column = "department_id"),
            @Result(property = "disease_name", column = "disease_name"),
            @Result(property = "department_name", column = "department_name")
    })
    List<question> getAllQuestions();

    /////////////////////////////////////
    @Select("select * from syf.questions where question_id=#{question_id}")
    question getQuestionByID(question q);

    @Select("select * from syf.questions where question_body=#{question_body}")
    question getQuestionByBody(question q);
//根据疾病name获取id，根据科室name获取id

    @Select("select disease_id from syf.disease where disease_name=#{disease_name}")
    String getDiseaseID(question q);

    @Select("select department_id from syf.department where department_name=#{department_name}")
    String getDepartmentID(question q);

    //模糊查询（包括题干和选项）
    @Select("select *  from syf.questions join syf.disease on syf.questions.disease_id=syf.disease.disease_id join syf.department on syf.questions.department_id=syf.department.department_id where question_body like concat('%',#{name},'%') or A like concat('%',#{name},'%') or B like concat('%',#{name},'%') or C like concat('%',#{name},'%') or D like concat('%',#{name},'%')")
    List<question> getQuestionByName(String name);

    //根据疾病name获取题目
    @Select("select * from syf.questions join syf.disease on syf.questions.disease_id=syf.disease.disease_id join syf.department on syf.questions.department_id=syf.department.department_id where disease_name=#{name}")
    List<question> getQuestionByDisease(String name);
}

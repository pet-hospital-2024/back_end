package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.paperDetail;
import com.example.pet_hospital.Entity.paper_question;
import com.example.pet_hospital.Entity.question;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;


@Mapper
public interface PaperMapper {
    @Insert("insert into syf.papers values (uuid_short(),#{duration},#{paper_name},null,null)")
    void createNewPaper(paper p);

    @Insert("insert into syf.paper_questions values (#{paper_id},#{question_id},#{value},#{order},null)")
    void insertNewQuestion(paper p);

    @Select("select * from syf.papers where paper_id=#{paper_id}")
    paper getPaperByID (paper p);

    @Select("SELECT * FROM syf.papers WHERE paper_id = #{paper_id}")
    @Results(value = {
            @Result(property = "paper_id", column = "paper_id"),
            @Result(property = "paper_name", column = "paper_name"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "question_number", column = "question_number"),
            @Result(property = "value", column = "value"),
            @Result(property = "questions", column = "paper_id", javaType = List.class,
                    many = @Many(select = "selectQuestionsForPaper"))
    })
    paperDetail getQuestionsFromPaper(String paper_id);

    @Select("SELECT q.* FROM syf.questions q INNER JOIN syf.paper_questions pq ON q.question_id = pq.question_id WHERE pq.paper_id = #{paper_id}")
    List<paper_question> selectQuestionsForPaper(String paper_id);


    @Select("select * from syf.papers where paper_name=#{paper_name}")
    paper getPaperByName (paper p);

    @Select("select * from syf.papers")
    ArrayList<paper> getPaperList ();

    @Delete("delete from syf.papers where paper_id=#{paper_id}")
    void deletePaperFromPapers(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id}")
    void deletePaperFromPaper_Questions(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id} and question_id=#{question_id}")
    void deleteQuestionFromPaper_Questions(paper p);

//    @Select("select question_id from syf.paper_questions where paper_id=#{paper_id}")
//    ArrayList<String> getQuestionsFromPaper(paper p);


}

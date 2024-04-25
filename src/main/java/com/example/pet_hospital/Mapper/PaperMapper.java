package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.paper;
import com.example.pet_hospital.Entity.question;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface PaperMapper {
    @Insert("insert into syf.papers values (uuid_short(),#{duration},#{paper_name},0,0)")
    void createNewPaper(paper p);

    @Insert("insert into syf.paper_questions values (#{paper_id},#{question_id},#{question_value},#{question_order},null)")
    void insertNewQuestion(paper p);

    @Select("select * from syf.papers where paper_id=#{paper_id}")
    paper getPaperByID(paper p);

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
    paper getQuestionsFromPaper(String paper_id);

    @Select("SELECT * FROM syf.questions q INNER JOIN syf.paper_questions pq ON q.question_id = pq.question_id WHERE pq.paper_id = #{paper_id} order by `order`")
    @Results(value = {
            @Result(property = "question_id", column = "question_id"),
            @Result(property = "question_body", column = "question_body"),
            @Result(property = "type", column = "type"),
            @Result(property = "order", column = "order"),
            @Result(property = "right_choice", column = "right_choice"),
            @Result(property = "judgement", column = "judgement"),
            @Result(property = "value", column = "value")

    })
    List<question> selectQuestionsForPaper(String paper_id);

    @Select("SELECT a,b,c,d FROM syf.questions WHERE question_id = #{question_id}")
    Map<String, String> selectOptionsForQuestion(String question_id);


    @Select("select * from syf.papers where paper_name=#{paper_name}")
    paper getPaperByName(paper p);

    @Select("select * from syf.papers")
    List<paper> getPaperList();

    @Delete("delete from syf.papers where paper_id=#{paper_id}")
    void deletePaperFromPapers(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id}")
    void deletePaperFromPaper_Questions(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id} and question_id=#{question_id}")
    void deleteQuestionFromPaper_Questions(paper p);


    @Select("select * from syf.paper_questions where paper_id=#{paper_id} and question_id=#{question_id}")
    List<Map<String, String>> ifPaperContainsQueston(paper p);

    @Select("select paper_id,question_id from syf.paper_questions where paper_id=#{paper_id} and `order` = #{question_order}")
    List<Map<String, String>> ifOrderExist(paper p);

    @Update("update syf.papers set value=#{value},question_number=#{questionNumber} where paper_id=#{paperId}")
    void updatePaperValueAndQuestionNumber(String paperId, int value, int questionNumber);

    @Update("update syf.papers set duration=#{duration},paper_name=#{paper_name} where paper_id=#{paper_id}")
    void changePaper(paper p);

    @Select("select question_id from syf.paper_questions where paper_id=#{paperId}")
    List<String> getQuestionsIDFromPaper(String paperId);

    @Update("update syf.paper_questions set `order`=#{i} where paper_id=#{paperId} and question_id=#{questionId}")
    void changePaperQuestion(String paperId, String questionId, int i);

    @Select("select `order` from syf.paper_questions where paper_id=#{paperId} and question_id=#{questionId}")
    int getQuestionOrder(String paperId, String questionId);

    @Delete("delete from syf.paper_questions where paper_id=#{paperID} and question_id=#{questionId}")
    void deletePaperQuestion(String paperID, String questionId);


//    @Select("select question_id from syf.paper_questions where paper_id=#{paper_id}")
//    ArrayList<String> getQuestionsFromPaper(paper p);


}

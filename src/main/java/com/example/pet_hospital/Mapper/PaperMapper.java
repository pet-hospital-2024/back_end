package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.paper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


@Mapper
public interface PaperMapper {
    @Insert("insert into syf.papers values (uuid_short(),#{duration},#{paper_name},null,null)")
    void createNewPaper(paper p);

    @Insert("insert into syf.paper_questions values (#{paper_id},#{question_id},#{value},#{order},null)")
    void insertNewQuestion(paper p);

    @Select("select * from syf.papers where paper_id=#{paper_id}")
    paper getPaperByID (paper p);

    @Select("select * from syf.papers where paper_name=#{paper_name}")
    paper getPaperByName (paper p);

    @Delete("delete from syf.papers where paper_id=#{paper_id}")
    void deletePaperFromPapers(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id}")
    void deletePaperFromPaper_Questions(paper p);

    @Delete("delete from syf.paper_questions where paper_id=#{paper_id} and question_id=#{question_id}")
    void deleteQuestionFromPaper_Questions(paper p);

    @Select("select question_id from syf.paper_questions where paper_id=#{paper_id}")
    ArrayList<String> getQuestionsFromPaper(paper p);


}

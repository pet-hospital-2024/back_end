package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.Exam;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ExamMapper {

    @Insert("insert into syf.exams values (uuid_short(),#{paper_id},#{exam_name},#{exam_start},#{exam_end})")
    void addExam(Exam exam);

    @Delete("delete from syf.exams where exam_id = #{exam_id}")
    void deleteExam(Exam exam);

    @Update("update syf.exams set paper_id = #{paper_id}, exam_name=#{exam_name}, exam_start = #{exam_start},exam_end = #{exam_end} where exam_id = #{exam_id}")
    void updateExam(Exam exam);

    @Select("select * from syf.exams where exam_id = #{exam_id}")
    Exam getExamById(Exam exam);

    @Select("select * from syf.exams where exam_name = #{exam_name}")
    Exam getExamByName(Exam exam);

    @Select("select e.exam_id, e.paper_id, p.paper_name,e.exam_name, p.duration, e.exam_start, e.exam_end from syf.exams e join syf.papers p on e.paper_id = p.paper_id")
    ArrayList<Exam> getExamList();


}

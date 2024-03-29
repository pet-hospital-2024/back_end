package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface DiseaseMapper {
    //编写sql语句
    //查询所有疾病

    @Insert("insert into syf.department values(uuid_short(),#{department_name})")
    void addDepartment(department k);

    @Select("select * from syf.department where department_name=#{department_name}")
    department getDepartmentbyName(department k);

    @Select("select * from syf.department where department_id=#{department_id}")
    department getDepartmentbyId(String id);

    @Select("select * from syf.department")
    department[] getAllDepartment();

    @Delete("delete from syf.department where department_id=#{department_id}")
    void deleteDepartment(department k);

    @Update("update syf.department set department_name=#{department_name} where department_id=#{department_id}")
    void changeDepartment(department k);

    @Insert("insert into syf.disease values(uuid_short(),#{department_id},#{disease_name})")
    void addDisease(disease d);

    @Select("select * from syf.disease where disease_name=#{disease_name} and department_id=#{department_id}")
    disease getDiseasebyName(disease d);

    @Delete("delete from syf.disease where disease_id=#{disease_id}")
    void deleteDisease(disease d);

    @Update("update syf.disease set disease_name=#{disease_name} where disease_id=#{disease_id}")
    void changeDiseaseName(disease d);

    @Select("select * from syf.disease where disease_id=#{disease_id}")
    disease getDiseasebyId(String disease_id);

    @Select("select disease_id, disease_name from syf.disease where department_id=#{department_id}")
    diseases[] getDiseasebyDepartment(String Department_id);

//    @Select("select case_id, case_name, case_introduction from syf.cases where disease_id=#{disease_id}")
//    List<case_base> getCasebyDis(String disease_id);

    @Select("select case_id, case_name, case_introduction from syf.cases where disease_id=#{disease_id}")
    case_base[] getCasebyDis(String disease_id);

    @Insert("insert into syf.cases values(uuid_short(),#{case_name},#{case_examination},#{case_result},#{case_treatment},#{case_medicine},#{case_cost},#{case_introduction},#{disease_id},#{department_id})")
    void addCase(cases i);

    @Select("select * from syf.cases where case_name=#{case_name}")
    cases getCasebyName(String case_name);

    @Select("select * from syf.cases where case_id=#{case_id}")
    cases getCasebyId(String case_id);

    @Delete("delete from syf.cases where case_id=#{case_id}")
    void deleteCase(cases i);

    @Update("update syf.cases set case_name=#{case_name},case_examination=#{case_examination},case_result=#{case_result},case_treatment=#{case_treatment},case_medicine=#{case_medicine},case_cost=#{case_cost},case_introduction=#{case_introduction},disease_id=#{disease_id},department_id=#{department_id} where case_id=#{case_id}")
    void changeCase(cases i);

    //根据病例名称模糊查询病例
    @Select("select * from syf.cases where case_name like CONCAT('%', #{case_name}, '%')")
    cases[] searchCase(String case_name);

    //查询所有病例
    @Select("select case_id, case_name, case_introduction from syf.cases ")
    List<case_base> CaseList();

    @Insert("insert into syf.case_img values(uuid_short(),#{case_id},#{case_img_url},#{case_img_name})")
    void addCaseImg(case_img i);

    @Delete("delete from syf.case_img where case_img_id=#{case_img_id}")
    void deleteCaseImg(case_img i);

    @Select("select * from syf.case_img where case_img_id=#{case_img_id}")
    case_img getCaseImgbyId(String case_img_id);

    @Select("select * from syf.case_img where case_id=#{case_id}")
    case_img[] getCaseImgbyCase(String case_id);

    @Insert("insert into syf.case_video values(uuid_short(),#{case_id},#{case_video_url},#{case_video_name})")
    void addCaseVideo(case_video i);

    @Delete("delete from syf.case_video where case_video_id=#{case_video_id}")
    void deleteCaseVideo(case_video i);

    @Select("select * from syf.case_video where case_video_id=#{case_video_id}")
    case_video getCaseVideobyId(String case_video_id);

    @Select("select * from syf.case_video where case_id=#{case_id}")
    case_video[] getCaseVideobyCase(String case_id);

    @Insert("insert into syf.operation_video values(uuid_short(),#{case_id},#{case_operation_url},#{case_operation_name})")
    void addOperationVideo(operation_video o);

    @Select("select * from syf.operation_video where case_operation_id=#{case_operation_id}")
    operation_video getOperationVideobyId(String case_operation_id);

    @Delete("delete from syf.operation_video where case_operation_id=#{case_operation_id}")
    void deleteCaseOperationVideo(operation_video o);

    @Select("select * from syf.operation_video where case_id=#{case_id}")
    operation_video[] getOperationVideobyCase(String case_id);

    @Insert("insert into syf.result_img values(uuid_short(),#{case_id},#{case_resultimg_url},#{case_resultimg_name})")
    void addResultImg(result_img r);

    @Select("select * from syf.result_img where case_resultimg_id=#{case_result_img_id}")
    result_img getResultImgbyId(String case_result_img_id);

    @Delete("delete from syf.result_img where case_resultimg_id=#{case_resultimg_id}")
    void deleteResultImg(result_img r);

    @Select("select * from syf.result_img where case_id=#{case_id}")
    result_img[] getCaseResultImgbyCase(String case_id);

    @Select("SELECT department.department_id, department.department_name, d.disease_id, d.disease_name " +
            "FROM syf.department " +
            "LEFT JOIN syf.disease d ON department.department_id = d.department_id " +
            "ORDER BY department.department_id, d.disease_id")
    @Results(value = {
            @Result(property = "department_id", column = "department_id"),
            @Result(property = "department_name", column = "department_name"),
            @Result(property = "diseases", column = "department_id", javaType = List.class,
                    many = @Many(select = "selectDiseasesForDepartment"))
    })
    List<department> findAllDepartments();

    @Select("SELECT disease_id, disease_name FROM syf.disease WHERE department_id = #{department_id}")
    @Results(value = {
            @Result(property = "disease_id", column = "disease_id"),
            @Result(property = "disease_name", column = "disease_name"),
    })
    List<diseases> selectDiseasesForDepartment(String departmentId);



}

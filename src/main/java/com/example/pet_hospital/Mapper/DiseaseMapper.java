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
    List<department> getAllDepartment();

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

    @Select("select * from syf.disease where department_id=#{department_id}")
    List<disease> getDiseasebyDepartment(String Department_id);

//    @Select("select case_id, case_name, case_introduction from syf.cases where disease_id=#{disease_id}")
//    List<case_base> getCasebyDis(String disease_id);

    @Select("select * from syf.cases where disease_id=#{disease_id}")
    cases[] getCasebyDis(String disease_id);

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
    @Select("select * from syf.cases ")
    List<cases> CaseList();



    @Select("SELECT department_id, department_name FROM syf.department ORDER BY department_id")
    @Results(value = {
            @Result(property = "department_id", column = "department_id"),
            @Result(property = "department_name", column = "department_name"),
            @Result(property = "diseases", column = "department_id", javaType = List.class,
                    many = @Many(select = "selectDiseasesForDepartment"))
    })
    List<department> findAllDepartments();

    @Insert("insert into syf.case_media values(uuid_short(),#{case_id},#{media_url},#{media_name},#{media_type},#{category})")
    void addMedia(case_media m);

    @Select("select * from syf.case_media where case_media_url=#{mediaUrl}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media getMediabyUrl(String mediaUrl);

    @Select("select * from syf.case_media where case_media_name=#{mediaName}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media getMediabyName(String mediaName);

    @Delete("delete from syf.case_media where case_media_id=#{media_id}")
    void deleteMedia(case_media m);


    @Select("select * from syf.case_media")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] findAllMedia();

    @Select("select * from syf.case_media where case_id=#{caseId}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByCaseId(String caseId);

    @Select("select * from syf.case_media where case_media_type=#{mediaType}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByType(String mediaType);

    @Select("select * from syf.case_media where category=#{category}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByCategory(String category);

    @Select("select * from syf.case_media where case_id=#{caseId} and case_media_type=#{mediaType}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByCaseIdAndType(String caseId, String mediaType);

    @Select("select * from syf.case_media where case_id=#{caseId} and category=#{category}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByCaseIdAndCategory(String caseId, String category);

    @Select("select * from syf.case_media where case_media_type=#{mediaType} and category=#{category}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByTypeAndCategory(String mediaType, String category);

    @Select("select * from syf.case_media where case_id=#{caseId} and case_media_type=#{mediaType} and category=#{category}")
    @Results(value = {
            @Result(property = "case_id", column = "case_id"),
            @Result(property = "media_id", column = "case_media_id"),
            @Result(property = "media_url", column = "case_media_url"),
            @Result(property = "media_name", column = "case_media_name"),
            @Result(property = "media_type", column = "case_media_type"),
            @Result(property = "category", column = "category")
    })
    case_media[] getMediaByCaseIdAndTypeAndCategory(String caseId, String mediaType, String category);
}

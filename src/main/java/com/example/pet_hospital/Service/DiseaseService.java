package com.example.pet_hospital.Service;


import com.example.pet_hospital.Entity.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DiseaseService {

    void addDepartment(department k);

    void deleteDepartment(department k);

    void changeDepartment(department k);

    department getDepartmentbyName(department k);//在科室表中根据科室名字查找科室

    department getDepartmentbyId(String id);//在科室表中根据科室id查找科室

    department[] getAllDepartment();

    void addDisease(disease d);

    void deleteDisease(disease d);

    void changeDiseaseName(disease d);

    disease getDiseasebyName(disease d);

    disease getDiseasebyId(String disease_id);

    diseases[] getDiseasebyDepartment(String department_id);//在疾病表中查找某一科室的所有疾病

    case_base[] getCasebyDis(String disease_id);//在病例表中查找某一疾病的所有病例

    void addCase(cases i);

    void deleteCase(cases i);

    void changeCase(cases i);

    cases getCasebyName(String case_name);

    cases getCasebyId(String case_id);

    cases[] searchCase(String name);

    //case_base[] CaseList();
    PageInfo<case_base> findPaginated(int page, int size);

    PageInfo<case_base> findPaginatedbyDis(String disease_id, int page, int size);

    void addCaseImg(case_img i);

    void deleteCaseImg(case_img i);

    case_img getCaseImgbyId(String caseImgId);//根据图片id查找图片

    case_img[] getCaseImgbyCase(String case_id);

    void addCaseVideo(case_video i);

    void deleteCaseVideo(case_video i);

    case_video getCaseVideobyId(String caseVideoId);//根据视频id查找视频

    case_video[] getCaseVideobyCase(String case_id);

    void addOperationVideo(operation_video o);

    operation_video getOperationVideobyId(String case_operation_id);

    void deleteCaseOperationVideo(operation_video o);

    operation_video[] getOperationVideobyCase(String caseId);

    void addResultImg(result_img r);

    result_img getResultImgbyId(String caseResultimgId);

    void deleteResultImg(result_img r);

    result_img[] getCaseResultImgbyCase(String caseId);

    List<department> findAllDepartments();


}

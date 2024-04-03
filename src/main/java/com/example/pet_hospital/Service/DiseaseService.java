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

    PageInfo<department> getAllDepartment(int page, int size);

    void addDisease(disease d);

    void deleteDisease(disease d);

    void changeDiseaseName(disease d);

    disease getDiseasebyName(disease d);

    disease getDiseasebyId(String disease_id);

    PageInfo<disease> getDiseasebyDepartment(String department_id, int page, int size);//在疾病表中查找某一科室的所有疾病

    cases[] getCasebyDis(String disease_id);//在病例表中查找某一疾病的所有病例

    void addCase(cases i);

    void deleteCase(cases i);

    void changeCase(cases i);

    cases getCasebyName(String case_name);

    cases getCasebyId(String case_id);

    cases[] searchCase(String name);

    //case_base[] CaseList();
    PageInfo<cases> findPaginated(int page, int size);

    //PageInfo<case_base> findPaginatedbyDis(String disease_id, int page, int size);





    List<department> findAllDepartments();


    void addMedia(case_media m);

    case_media getMediabyUrl(String mediaUrl);

    case_media getMediabyName(String mediaName);

    void deleteMedia(case_media m);

    case_media[] findAllMedia();

    case_media[] getMediaByCaseId(String caseId);

    case_media[] getMediaByType(String mediaType);

    case_media[] getMediaByCategory(String category);

    case_media[] getMediaByCaseIdAndType(String caseId, String mediaType);

    case_media[] getMediaByCaseIdAndCategory(String caseId, String category);

    case_media[] getMediaByTypeAndCategory(String mediaType, String category);

    case_media[] getMediaByCaseIdAndTypeAndCategory(String caseId, String mediaType, String category);
}

package com.example.pet_hospital.Service;


import com.example.pet_hospital.Entity.cases;
import com.example.pet_hospital.Entity.department;
import com.example.pet_hospital.Entity.disease;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.nio.file.Path;
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

    PageInfo<cases> searchCase(int page, int size, String case_name);

    //case_base[] CaseList();
    PageInfo<cases> findPaginated(int page, int size);

    //PageInfo<case_base> findPaginatedbyDis(String disease_id, int page, int size);


    List<department> findAllDepartments();


    cases[] findAllMedia();

    cases[] getMediaByCaseId(String caseId);

    cases[] getMediaByType(String mediaType);

    cases[] getMediaByCategory(String category);

    cases[] getMediaByCaseIdAndType(String caseId, String mediaType);

    cases[] getMediaByCaseIdAndCategory(String caseId, String category);

    cases[] getMediaByTypeAndCategory(String mediaType, String category);

    cases[] getMediaByCaseIdAndTypeAndCategory(String caseId, String mediaType, String category);

    void uploadMedia(cases m) throws Exception;

    void deleteMedia(String mediaId) throws Exception;

    String getMediabyId(String mediaId);

    void changeMedia(cases m) throws Exception;

    Path getFilePath(String filename);

    File mergeFiles(File[] files, String outputFileName) throws Exception;

    boolean allChunksExist(String baseFilename, int totalChunks);


    void uploadCompletedMedia(cases m, File mergedFile,String type) throws Exception;

    String calculateMD5(File file) throws Exception;

    boolean chunkExists(String baseFilename, int i);

    cases getMediabyUrl(String mediaUrl);

    void deleteMediaByUrl(String url) throws Exception;
}

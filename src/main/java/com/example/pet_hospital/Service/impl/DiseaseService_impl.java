package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Mapper.DiseaseMapper;
import com.example.pet_hospital.Service.DiseaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService_impl implements DiseaseService {
    @Autowired
    private DiseaseMapper DiseaseMapper;

    @Override
    public void addDepartment(department k) {
        DiseaseMapper.addDepartment(k);
    }

    @Override
    public department getDepartmentbyName(department k) {
        return DiseaseMapper.getDepartmentbyName(k);
    }

    @Override
    public department getDepartmentbyId(String id) {
        return DiseaseMapper.getDepartmentbyId(id);
    }

    @Override
    public PageInfo<department> getAllDepartment(int page, int size) {
        PageHelper.startPage(page, size);
        List<department> list = DiseaseMapper.getAllDepartment();
        PageInfo<department> pageInfo = new PageInfo<>(list, 5);
        return pageInfo;
    }

    @Override
    public void deleteDepartment(department k) {
        DiseaseMapper.deleteDepartment(k);
    }

    @Override
    public void changeDepartment(department k) {
        DiseaseMapper.changeDepartment(k);
    }

    @Override
    public void addDisease(disease d) {
        DiseaseMapper.addDisease(d);
    }

    @Override
    public disease getDiseasebyName(disease d) {
        return DiseaseMapper.getDiseasebyName(d);
    }

    @Override
    public void deleteDisease(disease d) {
        DiseaseMapper.deleteDisease(d);
    }

    @Override
    public void changeDiseaseName(disease d) {
        DiseaseMapper.changeDiseaseName(d);
    }

    @Override
    public disease getDiseasebyId(String disease_id) {
        return DiseaseMapper.getDiseasebyId(disease_id);
    }

    @Override
    public PageInfo<disease> getDiseasebyDepartment(String department_id, int page, int size) {
        PageHelper.startPage(page, size);
        List<disease> list = DiseaseMapper.getDiseasebyDepartment(department_id);
        PageInfo<disease> pageInfo = new PageInfo<>(list, 5);
        return pageInfo;
    }

    @Override
    public cases[] getCasebyDis(String disease_id) {
        return DiseaseMapper.getCasebyDis(disease_id);
    }

    @Override
    public void addCase(cases i) {
        DiseaseMapper.addCase(i);
    }

    @Override
    public cases getCasebyName(String case_name) {
        return DiseaseMapper.getCasebyName(case_name);
    }

    @Override
    public void deleteCase(cases i) {
        DiseaseMapper.deleteCase(i);
    }

    @Override
    public void changeCase(cases i) {
        DiseaseMapper.changeCase(i);
    }

    @Override
    public cases getCasebyId(String case_id) {
        return DiseaseMapper.getCasebyId(case_id);
    }

    @Override
    public cases[] searchCase(String case_name) {
        return DiseaseMapper.searchCase(case_name);
    }

    @Override
    public PageInfo<cases> findPaginated(int page, int size) {
        PageHelper.startPage(page, size);
        List<cases> list = DiseaseMapper.CaseList();
        //Page<case_base> pageList = (Page<case_base>) list;
        //System.out.println(pageInfo);
        //return new PageResult<>(list, pageInfo.getTotal(), pageInfo.getPages(), page, size);
        //return new PageResult<>(case_bases, case_bases.getTotal(), case_bases.getPages(), page, size);
        return new PageInfo<>(list, 5);
    }

//    @Override
//    public PageInfo<case_base> findPaginatedbyDis(String disease_id, int page, int size) {
//        PageHelper.startPage(page, size);
//        List<case_base> list = DiseaseMapper.getCasebyDis(disease_id);
//        PageInfo<case_base> pageInfo = new PageInfo<>(list, 5);
//        return pageInfo;
//    }


//    public case_base[] CaseList() {
//        return DiseaseMapper.CaseList();
//    }


    @Override
    public List<department> findAllDepartments() {
        return DiseaseMapper.findAllDepartments();
    }

    @Override
    public void addMedia(case_media m) {
        DiseaseMapper.addMedia(m);
    }

    @Override
    public case_media getMediabyUrl(String mediaUrl) {
        return DiseaseMapper.getMediabyUrl(mediaUrl);
    }

    @Override
    public case_media getMediabyName(String mediaName) {
        return DiseaseMapper.getMediabyName(mediaName);
    }

    @Override
    public void deleteMedia(case_media m) {
        DiseaseMapper.deleteMedia(m);

    }

    @Override
    public case_media[] findAllMedia() {
        return DiseaseMapper.findAllMedia();
    }

    @Override
    public case_media[] getMediaByCaseId(String caseId) {
        return DiseaseMapper.getMediaByCaseId(caseId);
    }

    @Override
    public case_media[] getMediaByType(String mediaType) {
        return DiseaseMapper.getMediaByType(mediaType);
    }

    @Override
    public case_media[] getMediaByCategory(String category) {
        return DiseaseMapper.getMediaByCategory(category);
    }

    @Override
    public case_media[] getMediaByCaseIdAndType(String caseId, String mediaType) {
        return DiseaseMapper.getMediaByCaseIdAndType(caseId, mediaType);
    }

    @Override
    public case_media[] getMediaByCaseIdAndCategory(String caseId, String category) {
        return DiseaseMapper.getMediaByCaseIdAndCategory(caseId, category);
    }

    @Override
    public case_media[] getMediaByTypeAndCategory(String mediaType, String category) {
        return DiseaseMapper.getMediaByTypeAndCategory(mediaType, category);
    }

    @Override
    public case_media[] getMediaByCaseIdAndTypeAndCategory(String caseId, String mediaType, String category) {
        return DiseaseMapper.getMediaByCaseIdAndTypeAndCategory(caseId, mediaType, category);
    }


}

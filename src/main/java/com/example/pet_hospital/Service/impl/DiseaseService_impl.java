package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Mapper.DiseaseMapper;
import com.example.pet_hospital.Service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService_impl implements DiseaseService {
    @Autowired
    private DiseaseMapper DiseaseMapper;

    public void addDepartment(department k) {
        DiseaseMapper.addDepartment(k);
    }

    public department getDepartmentbyName(department k) {
        return DiseaseMapper.getDepartmentbyName(k);
    }

    public department getDepartmentbyId(String id) {
        return DiseaseMapper.getDepartmentbyId(id);
    }

    public department[] getAllDepartment() {
        return DiseaseMapper.getAllDepartment();
    }

    public void deleteDepartment(department k) {
        DiseaseMapper.deleteDepartment(k);
    }

    public void changeDepartment(department k) {
        DiseaseMapper.changeDepartment(k);
    }

    public void addDisease(disease d) {
        DiseaseMapper.addDisease(d);
    }

    public disease getDiseasebyName(disease d) {
        return DiseaseMapper.getDiseasebyName(d);
    }

    public void deleteDisease(disease d) {
        DiseaseMapper.deleteDisease(d);
    }

    public void changeDiseaseName(disease d) {
        DiseaseMapper.changeDiseaseName(d);
    }

    public disease getDiseasebyId(String disease_id) {
        return DiseaseMapper.getDiseasebyId(disease_id);
    }

    public disease[] getDiseasebyDepartment(String department_id) {
        return DiseaseMapper.getDiseasebyDepartment(department_id);
    }

    public cases[] getCasebyDis(String disease_id) {
        return DiseaseMapper.getCasebyDis(disease_id);
    }

    public void addCase(cases i) {
        DiseaseMapper.addCase(i);
    }

    public cases getCasebyName(String case_name) {
        return DiseaseMapper.getCasebyName(case_name);
    }

    public void deleteCase(cases i) {
        DiseaseMapper.deleteCase(i);
    }

    public void changeCase(cases i) {
        DiseaseMapper.changeCase(i);
    }

    public cases getCasebyId(String case_id) {
        return DiseaseMapper.getCasebyId(case_id);
    }

    public cases[] searchCase(String case_name) {
        return DiseaseMapper.searchCase(case_name);
    }

    @Override
    public void addCaseImg(case_img i) {
        DiseaseMapper.addCaseImg(i);
    }

    @Override
    public void deleteCaseImg(case_img i) {
        DiseaseMapper.deleteCaseImg(i);
    }

    @Override
    public case_img getCaseImgbyId(String case_imgage_id) {
        return DiseaseMapper.getCaseImgbyId(case_imgage_id);
    }

    @Override
    public case_img[] getCaseImgbyCase(String case_id) {
        return DiseaseMapper.getCaseImgbyCase(case_id);
    }

    @Override
    public void addCaseVideo(case_video i) {
        DiseaseMapper.addCaseVideo(i);
    }

    @Override
    public void deleteCaseVideo(case_video i) {
        DiseaseMapper.deleteCaseVideo(i);
    }

    @Override
    public case_video getCaseVideobyId(String caseVideoId) {
        return DiseaseMapper.getCaseVideobyId(caseVideoId);
    }

    @Override
    public case_video[] getCaseVideobyCase(String case_id) {
        return DiseaseMapper.getCaseVideobyCase(case_id);
    }

    @Override
    public void addOperationVideo(operation_video o) {
        DiseaseMapper.addOperationVideo(o);
    }

    @Override
    public operation_video getOperationVideobyId(String case_operation_id) {
        return DiseaseMapper.getOperationVideobyId(case_operation_id);
    }

    @Override
    public void deleteCaseOperationVideo(operation_video o) {
        DiseaseMapper.deleteCaseOperationVideo(o);

    }

    @Override
    public operation_video[] getOperationVideobyCase(String case_id) {
        return DiseaseMapper.getOperationVideobyCase(case_id);
    }

    @Override
    public void addResultImg(result_img r) {
        DiseaseMapper.addResultImg(r);

    }

    @Override
    public result_img getResultImgbyId(String case_result_img_id) {
        return DiseaseMapper.getResultImgbyId(case_result_img_id);
    }

    @Override
    public void deleteResultImg(result_img r) {
        DiseaseMapper.deleteResultImg(r);

    }

    @Override
    public result_img[] getCaseResultImgbyCase(String case_id) {
        return DiseaseMapper.getCaseResultImgbyCase(case_id);
    }
}

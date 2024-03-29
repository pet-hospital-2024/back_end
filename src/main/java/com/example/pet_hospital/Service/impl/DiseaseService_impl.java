package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.*;
import com.example.pet_hospital.Mapper.DiseaseMapper;
import com.example.pet_hospital.Service.DiseaseService;
import com.example.pet_hospital.model.PageResult;
import com.github.pagehelper.Page;
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
    public department[] getAllDepartment() {
        return DiseaseMapper.getAllDepartment();
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
    public diseases[] getDiseasebyDepartment(String department_id) {
        return DiseaseMapper.getDiseasebyDepartment(department_id);
    }

    @Override
    public case_base[] getCasebyDis(String disease_id) {
        return null;
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
    public PageInfo<case_base> findPaginated(int page, int size) {
        PageHelper.startPage(page, size);
        List<case_base> list = DiseaseMapper.CaseList();
        //Page<case_base> pageList = (Page<case_base>) list;
        PageInfo<case_base> pageInfo = new PageInfo<>(list, 5);
        //System.out.println(pageInfo);
        //return new PageResult<>(list, pageInfo.getTotal(), pageInfo.getPages(), page, size);
        //return new PageResult<>(case_bases, case_bases.getTotal(), case_bases.getPages(), page, size);
        return pageInfo;
    }

    @Override
    public PageInfo<case_base> findPaginatedbyDis(String disease_id, int page, int size) {
        PageHelper.startPage(page, size);
        List<case_base> list = DiseaseMapper.getCasebyDis(disease_id);
        PageInfo<case_base> pageInfo = new PageInfo<>(list, 5);
        return pageInfo;
    }


//    public case_base[] CaseList() {
//        return DiseaseMapper.CaseList();
//    }

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

    @Override
    public List<department> findAllDepartments() {
        return DiseaseMapper.findAllDepartments();
    }
}

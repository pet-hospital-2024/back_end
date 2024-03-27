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

    public void addKind(kind k) {
        DiseaseMapper.addKind(k);
    }

    public kind getKindbyName(kind k) {
        return DiseaseMapper.getKindbyName(k);
    }

    public kind getKindbyId(String id) {
        return DiseaseMapper.getKindbyId(id);
    }

    public kind[] getAllKind() {
        return DiseaseMapper.getAllKind();
    }

    public void deleteKind(kind k) {
        DiseaseMapper.deleteKind(k);
    }

    public void changeKind(kind k) {
        DiseaseMapper.changeKind(k);
    }

    public void addDis(disease d) {
        DiseaseMapper.addDis(d);
    }

    public disease getDisbyName(disease d) {
        return DiseaseMapper.getDisbyName(d);
    }

    public void deleteDis(disease d) {
        DiseaseMapper.deleteDis(d);
    }

    public void changeDis(disease d) {
        DiseaseMapper.changeDis(d);
    }

    public disease getDisbyId(String uuid) {
        return DiseaseMapper.getDisbyId(uuid);
    }

    public disease[] searchbyKind(String kind_id) {
        return DiseaseMapper.searchbyKind(kind_id);
    }

    public instance[] getInstancebyDis(String dis_id) {
        return DiseaseMapper.getInstancebyDis(dis_id);
    }

    public void addInstance(instance i) {
        DiseaseMapper.addInstance(i);
    }

    public instance getInstancebyName(String name) {
        return DiseaseMapper.getInstancebyName(name);
    }

    public void deleteInstance(instance i) {
        DiseaseMapper.deleteInstance(i);
    }

    public void changeInstance(instance i) {
        DiseaseMapper.changeInstance(i);
    }

    public instance getInstancebyId(String instance_id) {
        return DiseaseMapper.getInstancebyId(instance_id);
    }

    public instance[] searchInstance(String name) {
        return DiseaseMapper.searchInstance(name);
    }

    @Override
    public void addInstanceImg(instance_img i) {
        DiseaseMapper.addInstanceImg(i);
    }

    @Override
    public void deleteInstanceImg(instance_img i) {
        DiseaseMapper.deleteInstanceImg(i);
    }

    @Override
    public instance_img getInstanceImgbyId(String instanceImgId) {
        return DiseaseMapper.getInstanceImgbyId(instanceImgId);
    }

    @Override
    public instance_img[] getInstanceImgbyInstance(String instanceId) {
        return DiseaseMapper.getInstanceImgbyInstance(instanceId);
    }

    @Override
    public void addInstanceVideo(instance_video i) {
        DiseaseMapper.addInstanceVideo(i);
    }

    @Override
    public void deleteInstanceVideo(instance_video i) {
        DiseaseMapper.deleteInstanceVideo(i);
    }

    @Override
    public instance_video getInstanceVideobyId(String instanceVideoId) {
        return DiseaseMapper.getInstanceVideobyId(instanceVideoId);
    }

    @Override
    public instance_video[] getInstanceVideobyInstance(String instanceId) {
        return DiseaseMapper.getInstanceVideobyInstance(instanceId);
    }

    @Override
    public void addIntanceOperationVideo(operation_video o) {
        DiseaseMapper.addIntanceOperationVideo(o);
    }

    @Override
    public operation_video getOperationVideobyId(String instanceOperationId) {
        return DiseaseMapper.getOperationVideobyId(instanceOperationId);
    }

    @Override
    public void deleteInstanceOperationVideo(operation_video o) {
        DiseaseMapper.deleteInstanceOperationVideo(o);

    }

    @Override
    public operation_video[] getOperationVideobyInstance(String instanceId) {
        return DiseaseMapper.getOperationVideobyInstance(instanceId);
    }

    @Override
    public void addResultImg(result_img r) {
        DiseaseMapper.addResultImg(r);

    }

    @Override
    public result_img getResultImgbyId(String instanceResultimgId) {
        return DiseaseMapper.getResultImgbyId(instanceResultimgId);
    }

    @Override
    public void deleteResultImg(result_img r) {
        DiseaseMapper.deleteResultImg(r);

    }

    @Override
    public result_img[] getInstanceResultImgbyInstance(String instanceId) {
        return DiseaseMapper.getInstanceResultImgbyInstance(instanceId);
    }
}

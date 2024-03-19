package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Mapper.DiseaseMapper;
import com.example.pet_hospital.Service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pet_hospital.Entity.kind;

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

    /*public kind getKindbyId(disease d) {
        return DiseaseMapper.getKindbyId(d);
    }*/

    public void deleteDis(disease d) {
        DiseaseMapper.deleteDis(d);
    }

    public void changeDis(disease d) {
        DiseaseMapper.changeDis(d);
    }

    public disease getDisbyId(disease d) {
        return DiseaseMapper.getDisbyId(d);
    }

    public disease[] searchbyKind(String kind_id) {
        return DiseaseMapper.searchbyKind(kind_id);
    }
}

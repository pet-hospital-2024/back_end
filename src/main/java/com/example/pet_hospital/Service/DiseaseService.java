package com.example.pet_hospital.Service;


import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Entity.kind;

public interface DiseaseService {
    void addKind(kind k);
    kind getKindbyName(kind k);
    void deleteKind(kind k);
    void changeKind(kind k);
    kind getKindbyId(String id);
    kind[] getAllKind();
    void addDis(disease d);
    disease getDisbyName(disease d);
    //kind getKindbyId(disease d);
    void deleteDis(disease d);
    void changeDis(disease d);
    disease getDisbyId(disease d);
    disease[] searchbyKind(String kind_id);
}

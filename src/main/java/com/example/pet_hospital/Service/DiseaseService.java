package com.example.pet_hospital.Service;


import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Entity.instance;
import com.example.pet_hospital.Entity.kind;

public interface DiseaseService {
    void addKind(kind k);
    void deleteKind(kind k);
    void changeKind(kind k);
    kind getKindbyName(kind k);//在科室表中根据科室名字查找科室
    kind getKindbyId(String id);//在科室表中根据科室id查找科室
    kind[] getAllKind();
    void addDis(disease d);
    void deleteDis(disease d);
    void changeDis(disease d);
    disease getDisbyName(disease d);
    disease getDisbyId(String uuid);
    disease[] searchbyKind(String kind_id);//在疾病表中查找某一科室的所有疾病
    instance[] getInstancebyDis(String dis_id);//在病例表中查找某一疾病的所有病例
    void addInstance(instance i);
    void deleteInstance(instance i);
    void changeInstance(instance i);
    instance getInstancebyName(String name);
    instance getInstancebyId(String uuid);
    instance[] searchInstance(String name);
}

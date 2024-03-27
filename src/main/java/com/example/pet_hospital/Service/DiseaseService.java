package com.example.pet_hospital.Service;


import com.example.pet_hospital.Entity.*;

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
    instance getInstancebyId(String instance_id);
    instance[] searchInstance(String name);

    void  addInstanceImg(instance_img i);

    void deleteInstanceImg(instance_img i);

    instance_img getInstanceImgbyId(String instanceImgId);//根据图片id查找图片

    instance_img[] getInstanceImgbyInstance(String instanceId);

    void addInstanceVideo(instance_video i);

    void deleteInstanceVideo(instance_video i);

    instance_video getInstanceVideobyId(String instanceVideoId);//根据视频id查找视频

    instance_video[] getInstanceVideobyInstance(String instanceId);

    void addIntanceOperationVideo(operation_video o);

    operation_video getOperationVideobyId(String instanceOperationId);

    void deleteInstanceOperationVideo(operation_video o);

    operation_video[] getOperationVideobyInstance(String instanceId);

    void addResultImg(result_img r);

    result_img getResultImgbyId(String instanceResultimgId);

    void deleteResultImg(result_img r);

    result_img[] getInstanceResultImgbyInstance(String instanceId);
}

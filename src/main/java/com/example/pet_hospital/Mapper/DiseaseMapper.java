package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.*;
import org.apache.ibatis.annotations.*;


@Mapper
public interface DiseaseMapper {
    //编写sql语句
    //查询所有疾病

    @Insert("insert into syf.kind values(uuid_short(),#{name})")
    void addKind(kind k);

    @Select("select * from syf.kind where name=#{name}")
    kind getKindbyName(kind k);

    @Select("select * from syf.kind where kind_id=#{kind_id}")
    kind getKindbyId(String id);

    @Select("select * from syf.kind")
    kind[] getAllKind();

    @Delete("delete from syf.kind where kind_id=#{kind_id}")
    void deleteKind(kind k);

    @Update("update syf.kind set name=#{name} where kind_id=#{kind_id}")
    void changeKind(kind k);

    @Insert("insert into syf.dis values(uuid_short(),#{kind_id},#{name})")
    void addDis(disease d);

    @Select("select * from syf.dis where name=#{name} and kind_id=#{kind_id}")
    disease getDisbyName(disease d);

    @Delete("delete from syf.dis where dis_id=#{dis_id}")
    void deleteDis(disease d);

    @Update("update syf.dis set name=#{name} where dis_id=#{dis_id}")
    void changeDis(disease d);

    @Select("select * from syf.dis where dis_id=#{dis_id}")
    disease getDisbyId(String uuid);

    @Select("select * from syf.dis where kind_id=#{kind_id}")
    disease[] searchbyKind(String kind_id);

    @Select("select instance_id, name, intro from syf.instance where dis_id=#{dis_id}")
    instance[] getInstancebyDis(String dis_id);

    @Insert("insert into syf.instance values(uuid_short(),#{name},#{text},#{result},#{treatment},#{medicine},#{price},#{intro},#{dis_id},#{kind_id})")
    void addInstance(instance i);

    @Select("select * from syf.instance where name=#{name}")
    instance getInstancebyName(String name);

    @Select("select * from syf.instance where instance_id=#{instance_id}")
    instance getInstancebyId(String instance_id);

    @Delete("delete from syf.instance where instance_id=#{instance_id}")
    void deleteInstance(instance i);

    @Update("update syf.instance set name=#{name},text=#{text},result=#{result},treatment=#{treatment},medicine=#{medicine},price=#{price},intro=#{intro},dis_id=#{dis_id},kind_id=#{kind_id} where instance_id=#{instance_id}")
    void changeInstance(instance i);

    //根据病例名称模糊查询病例
    @Select("select * from syf.instance where name like CONCAT('%', #{name}, '%')")
    instance[] searchInstance(String name);

    @Insert("insert into syf.instance_img values(uuid_short(),#{instance_id},#{instance_img_url},#{instance_img_name})")
    void addInstanceImg(instance_img i);

    @Delete("delete from syf.instance_img where instance_img_id=#{instance_img_id}")
    void deleteInstanceImg(instance_img i);

    @Select("select * from syf.instance_img where instance_img_id=#{instanceImgId}")
    instance_img getInstanceImgbyId(String instanceImgId);

    @Select("select * from syf.instance_img where instance_id=#{instanceId}")
    instance_img[] getInstanceImgbyInstance(String instanceId);

    @Insert("insert into syf.instance_video values(uuid_short(),#{instance_id},#{instance_video_url},#{instance_video_name})")
    void addInstanceVideo(instance_video i);

    @Delete("delete from syf.instance_video where instance_video_id=#{instance_video_id}")
    void deleteInstanceVideo(instance_video i);

    @Select("select * from syf.instance_video where instance_video_id=#{instanceVideoId}")
    instance_video getInstanceVideobyId(String instanceVideoId);

    @Select("select * from syf.instance_video where instance_id=#{instanceId}")
    instance_video[] getInstanceVideobyInstance(String instanceId);

    @Insert("insert into syf.operation_video values(uuid_short(),#{instance_id},#{instance_operation_url},#{instance_operation_name})")
    void addIntanceOperationVideo(operation_video o);

    @Select("select * from syf.operation_video where instance_operation_id=#{instanceOperationId}")
    operation_video getOperationVideobyId(String instanceOperationId);

    @Delete("delete from syf.operation_video where instance_operation_id=#{instance_operation_id}")
    void deleteInstanceOperationVideo(operation_video o);

    @Select("select * from syf.operation_video where instance_id=#{instanceId}")
    operation_video[] getOperationVideobyInstance(String instanceId);

    @Insert("insert into syf.result_img values(uuid_short(),#{instance_id},#{instance_resultimg_url},#{instance_resultimg_name})")
    void addResultImg(result_img r);

    @Select("select * from syf.result_img where instance_resultimg_id=#{instanceResultimgId}")
    result_img getResultImgbyId(String instanceResultimgId);

    @Delete("delete from syf.result_img where instance_resultimg_id=#{instance_resultimg_id}")
    void deleteResultImg(result_img r);

    @Select("select * from syf.result_img where instance_id=#{instanceId}")
    result_img[] getInstanceResultImgbyInstance(String instanceId);
}

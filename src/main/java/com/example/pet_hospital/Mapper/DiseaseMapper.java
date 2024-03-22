package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Entity.instance;
import org.apache.ibatis.annotations.*;
import com.example.pet_hospital.Entity.kind;


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

}

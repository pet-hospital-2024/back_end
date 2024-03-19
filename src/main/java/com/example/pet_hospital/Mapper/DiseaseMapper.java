package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.disease;
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

    @Select("select * from syf.kind where id=#{id}")
    kind getKindbyId(String id);

    @Select("select * from syf.kind")
    kind[] getAllKind();

    @Delete("delete from syf.kind where id=#{id}")
    void deleteKind(kind k);

    @Update("update syf.kind set name=#{name} where id=#{id}")
    void changeKind(kind k);

    @Insert("insert into syf.dis values(uuid_short(),#{kind_id},#{name})")
    void addDis(disease d);

    @Select("select * from syf.dis where name=#{name} and kind_id=#{kind_id}")
    disease getDisbyName(disease d);

    @Delete("delete from syf.dis where uuid=#{uuid}")
    void deleteDis(disease d);

    @Update("update syf.disease set kind_id=#{kind_id},name=#{name} where uuid=#{uuid}")
    void changeDis(disease d);

    @Select("select * from syf.dis where uuid=#{uuid}")
    disease getDisbyId(disease d);

    @Select("select * from syf.dis where kind_id=#{kind_id}")
    disease[] searchbyKind(String kind_id);
}

package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LearnMapper {

    @Select("select * from syf.learn_order where learn_role=#{role} order by learn_order")
    learn_order[] getLearnOrder(String role);

    @Select("select * from syf.learn_location")
    learn_location[] getLocations();
}


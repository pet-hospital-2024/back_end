package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.learn_item;
import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LearnMapper {

    @Select("select learn_order_id,learn_order,learn_role,learn_location_name,learn_duty from syf.learn_order where learn_role=#{role} order by learn_order")
    learn_order[] getLearnOrder(String role);

    @Select("select * from syf.learn_location")
    learn_location[] getLocations();


    @Select("select learn_item_url,learn_item_name,learn_text from syf.learn_order where learn_location_name=#{locationName} and learn_role=#{role}")
    learn_item getLearnItem(String locationName, String role);
}


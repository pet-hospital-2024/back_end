package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.learn_item;
import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;
import com.example.pet_hospital.Entity.learn_process;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LearnMapper {

    @Select("select ll.learn_location_name,learn_location_id,learn_duty from syf.learn_order left join syf.learn_location ll on learn_order.learn_location_name = ll.learn_location_name where learn_role=#{role}")
    @Result(column = "learn_location_name", property = "location_name")
    @Result(column = "learn_location_id", property = "location_id")
    @Result(column = "learn_duty", property = "learn_duty")
    learn_order[] getLearnOrder(String role);

    @Select("select * from syf.learn_location")
    @Result(column = "learn_location_id", property = "location_id")
    @Result(column = "learn_location_name", property = "location_name")
    @Result(column = "learn_location_introduction", property = "location_introduction")
    learn_location[] getLocations();


    @Select("select learn_item_url,learn_item_name,learn_text from syf.learn_order where learn_location_name=#{locationName} and learn_role=#{role}")
    learn_item getLearnItem(String locationName, String role);



    @Select("select learn_process_order,learn_process_name from syf.learn_process where learn_role=#{role}")
    @Result(column = "learn_process_order", property = "process_order")
    @Result(column = "learn_process_name", property = "process_name")
    learn_process[] getLearnProcess(String role);

    @Select("select learn_location_name from syf.learn_location where learn_location_id=#{locationId}")
    String getLocationName(String locationId);
}


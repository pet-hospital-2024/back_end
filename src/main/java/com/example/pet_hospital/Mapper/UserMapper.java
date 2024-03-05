package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.user;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from syf.userdata where username=#{username} and password=#{password}")
    user getUserByUserNameAndPassword(user u);

    @Insert("insert into syf.userdata values(#{username},#{password},#{identity},#{phone_number},#{email},#{timestamp})")
    void insertUser(user u);

    @Select("select * from syf.userdata where username=#{username}")
    user getUser(user u);

    @Delete("delete from syf.userdata where username=#{username}")
    void deleteUser(user u);

    @Update("update syf.userdata set identity=#{banned} where username=#{username}")
    void banUser(user u);

    @Update("update syf.userdata set username=#{username}, password=#{password}, phone_number=#{phone_number}, email=#{email} where user_id=#{user_id}")
    void alterUserInfo(user u);
}

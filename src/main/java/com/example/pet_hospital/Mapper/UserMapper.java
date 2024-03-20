package com.example.pet_hospital.Mapper;

import com.example.pet_hospital.Entity.user;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@Mapper
public interface UserMapper {
    @Select("select * from syf.userdata where username=#{username} and password=#{password}")
    user getUserByUserNameAndPassword(user u);

    @Select("select * from syf.userdata where username=#{username}")
    user getUserByUserName(user u);

    @Update("update syf.userdata set timestamp=localtimestamp() where username=#{username}")
    void refreshTimestamp(user u);


    @Insert("insert into syf.userdata values(uuid_short(),#{username},#{password},#{identity},#{phone_number},#{email},localtimestamp())")
    void insertUser(user u);

    @Select("select * from syf.userdata where username=#{username}")
    user getUser(user u);

    @Select("select * from syf.userdata where user_id=#{user_id}")
    user getUserByID(user u);

    @Delete("delete from syf.userdata where username=#{username}")
    void deleteUser(user u);

    @Update("update syf.userdata set identity='banned' where username=#{username}")
    void banUser(user u);

    @Update("update syf.userdata set username=#{username}, password=#{password}, phone_number=#{phone_number}, email=#{email} where user_id=#{user_id}")
    void alterUserInfo(user u);
}

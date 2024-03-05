package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.pet_hospital.Service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserService_impl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public user login(user u){
        return userMapper.getUserByUserNameAndPassword(u);
    }

    @Override
    public void register(user u) {
        userMapper.insertUser(u);
    }

    @Override
    public Boolean findUser(user u) {
        if (userMapper.getUser(u)!=null)
            return true;
        else
            return false;
    }

    @Override
    public void deleteUser(user u) {
        userMapper.deleteUser(u);
    }

    @Override
    public void banUser(user u) {
        userMapper.banUser(u);
    }

    @Override
    public void alterUserInfo(user u) {
        userMapper.alterUserInfo(u);
    }

}

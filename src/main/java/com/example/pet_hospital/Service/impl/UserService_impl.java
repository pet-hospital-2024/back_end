package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.user;
import com.example.pet_hospital.Mapper.UserMapper;
import com.example.pet_hospital.Service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService_impl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public user login(user u){
        userMapper.refreshTimestamp(u);
        return userMapper.getUserByUserNameAndPassword(u);
    }

    @Override
    public void register(user u) {
        userMapper.insertUser(u);
    }

    @Override
    public Boolean findUser(user u) {
        return userMapper.getUser(u) != null;
    }

    @Override
    public PageInfo<user> getAllUser(int page, int size) {
        PageHelper.startPage(page, size);
        List<user> users = userMapper.getAllUser();
        return new PageInfo<>(users);
    }

    @Override
    public user getUserByID(user u) {
        return userMapper.getUserByID(u);
    }

    @Override
    public user getUserByName(user u) {
        return userMapper.getUserByUserName(u);
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
        userMapper.refreshTimestamp(u);
        userMapper.alterUserInfo(u);
    }

    @Override
    public void ChangePassword(user u) {
        userMapper.ChangePassword(u);
    }

    @Override
    public PageInfo<user> getUserByName(String name, int page, int size) {
        PageHelper.startPage(page, size);
        List<user> users = userMapper.getUserByName(name);
        return new PageInfo<>(users);
    }

}

package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.user;
import com.github.pagehelper.PageInfo;

public interface UserService {
    user login(user u);

    void register(user u);

    Boolean findUser(user u);

    PageInfo<user> getAllUser(int page, int size);

    user getUserByID(user u);

    public user getUserByName(user u);
    void deleteUser(user u);

    void banUser(user u);

    void alterUserInfo(user u);

    void ChangePassword(user u);
}

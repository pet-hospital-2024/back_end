package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.user;

public interface UserService {
    user login(user u);
    void register(user u);

    Boolean findUser(user u);

    void deleteUser(user u);

    void banUser(user u);

    void alterUserInfo(user u);
}

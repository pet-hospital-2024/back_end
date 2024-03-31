package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.learn_item;
import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;
import com.example.pet_hospital.Entity.learn_process;
import com.example.pet_hospital.Mapper.LearnMapper;
import com.example.pet_hospital.Service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnService_impl implements LearnService {

    @Autowired
    private LearnMapper learnMapper;


    @Override
    public learn_order[] getLearnOrder(String role) {
        return learnMapper.getLearnOrder(role);
    }

    @Override
    public learn_location[] getLocations() {
        return learnMapper.getLocations();
    }

    @Override
    public learn_item getLearnItem(String locationName, String role) {
        return learnMapper.getLearnItem(locationName, role);
    }

    @Override
    public learn_process[] getLearnProcess(String role) {
        return learnMapper.getLearnProcess(role);
    }

    @Override
    public String getLocationName(String locationId) {
        return learnMapper.getLocationName(locationId);
    }
}

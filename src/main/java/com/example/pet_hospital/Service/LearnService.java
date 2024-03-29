package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;

public interface LearnService {

    learn_order[] getLearnOrder(String role);

//    List getLearnItems(String locationId);

    learn_location[] getLocations();
}

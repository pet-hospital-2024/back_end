package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.learn_item;
import com.example.pet_hospital.Entity.learn_location;
import com.example.pet_hospital.Entity.learn_order;
import com.example.pet_hospital.Entity.learn_process;

public interface LearnService {

    learn_order[] getLearnOrder(String role);

//    List getLearnItems(String locationId);

    learn_location[] getLocations();

    learn_item getLearnItem(String locationName, String role);

    learn_process[] getLearnProcess(String role);

    String getLocationName(String locationId);
}

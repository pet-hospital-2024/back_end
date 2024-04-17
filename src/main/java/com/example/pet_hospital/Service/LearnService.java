package com.example.pet_hospital.Service;

import com.example.pet_hospital.Entity.learn;

public interface LearnService {

    learn[] getLearnOrder(String role);

//    List getLearnItems(String locationId);

    learn[] getLocations();

    learn getLearnItem(String locationName, String role);

    learn[] getLearnProcess(String role);

    String getLocationName(String locationId);
}

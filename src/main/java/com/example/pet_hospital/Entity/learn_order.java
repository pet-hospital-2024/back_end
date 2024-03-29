package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class learn_order {
    String learn_order_id;
    String learn_order;
    String learn_role;
    String learn_location;
    String learn_duty;
    String learn_item_url;
}

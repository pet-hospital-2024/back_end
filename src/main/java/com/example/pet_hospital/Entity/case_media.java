package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class case_media {
    String case_id;
    String media_id;
    String media_url;
    String media_name;
    String media_type;
    String category;
}

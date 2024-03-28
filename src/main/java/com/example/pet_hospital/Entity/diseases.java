package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//返回科室下疾病专用

@Data
@AllArgsConstructor
@NoArgsConstructor
public class diseases {
    String disease_id;
    String disease_name;
}

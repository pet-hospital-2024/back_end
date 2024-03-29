package com.example.pet_hospital.Entity;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class case_video {
    String case_video_id;
    String case_id;
    String case_video_url;
    String case_video_name;


}

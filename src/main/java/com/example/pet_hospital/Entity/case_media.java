package com.example.pet_hospital.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class case_media {
    MultipartFile file;
    String case_id;
    String media_id;
    String media_url;
    String media_name;
    String media_type;
    String category;
    String created_at;
    String updated_at;

}

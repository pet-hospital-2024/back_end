package com.example.pet_hospital.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class file_upload {

    private Long id;
    private String upload_id;  // 业务层面的唯一标识符
    private String case_id;
    private String category;
    private String filename;
    private Integer total_chunks;
    private Integer uploaded_chunks;
    private String status;
    private String last_updated;


}

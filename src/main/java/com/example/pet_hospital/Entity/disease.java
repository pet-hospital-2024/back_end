package com.example.pet_hospital.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class disease {
    String disease_id;//疾病id
    String department_id;//科室id
    String disease_name;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    department department;
    public String getDepartment_id() {
        return department_id;
    }

}

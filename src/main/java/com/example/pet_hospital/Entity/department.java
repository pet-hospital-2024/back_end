package com.example.pet_hospital.Entity;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class department {
    String department_id;
    String department_name;

    @OneToMany(mappedBy = "department_id")
    List<disease> diseases;
}

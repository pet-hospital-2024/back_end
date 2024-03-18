package com.example.pet_hospital.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class operation {
    String user_id;
    String username;
    String password;
    String identity;
    String phone_number;
    String email;
    String timestamp;
    String token;
}

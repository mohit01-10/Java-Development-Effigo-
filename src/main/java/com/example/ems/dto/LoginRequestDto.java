package com.example.ems.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String email;   // ✅ Changed from `username` to `email`
    private String password;
}

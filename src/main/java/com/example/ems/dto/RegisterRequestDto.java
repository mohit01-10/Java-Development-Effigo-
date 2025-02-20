package com.example.ems.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String name;     //  Added name
    private String email;    // Email is used for authentication
    private String password; 
    private String phone;    //  Added phone number
    private int roleId;

}


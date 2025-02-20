package com.example.ems.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserDto {
    private String name;
    private String email;
    private String phone;
    private Integer role; // Optional: Only change role if provided
}

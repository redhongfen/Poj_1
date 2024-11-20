package com.example.poj_1.eneity;

import lombok.Data;

@Data
public class Users {
    private Integer user_id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String token;
}

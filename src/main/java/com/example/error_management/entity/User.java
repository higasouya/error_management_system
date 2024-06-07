package com.example.error_management.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private  String login_id;
    private String password;
    private String name;


}

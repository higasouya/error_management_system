package com.example.error_management.entity;

import lombok.Data;

@Data
public class Case {

    private int id;
    private int error_id;
    private String description;
    private boolean solve;
    private String solution;
    private String user_name;
    private int user_id;
}

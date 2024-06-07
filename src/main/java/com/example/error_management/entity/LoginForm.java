package com.example.error_management.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {
    @NotEmpty(message = "IDは必須です")
    private String loginId;
    @NotEmpty(message = "PASSは必須です")
    private String password;
}

package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class UserLoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public UserLoginDTO(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

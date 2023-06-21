package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class SendCodeDTO {

    @NotNull
    private String email;

    public SendCodeDTO(String email) {
        this.email = email;
    }

    public SendCodeDTO() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

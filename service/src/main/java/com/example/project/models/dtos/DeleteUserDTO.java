package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class DeleteUserDTO {

    @NotNull
    private Integer id_user;

    public DeleteUserDTO(Integer id_user) {
        super();
        this.id_user = id_user;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}

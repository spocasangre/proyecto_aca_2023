package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class ChangePasswordDTO {

    @NotNull
    private String codigo;

    @NotNull
    private String password;

    public ChangePasswordDTO(String codigo, String password) {
        super();
        this.codigo = codigo;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

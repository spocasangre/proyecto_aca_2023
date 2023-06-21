package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class CreateContactDTO {

    @NotNull
    private String name;

    @NotNull
    private Integer number;

    @NotNull
    private Long id_user;

    @NotNull
    private String email;

    public CreateContactDTO(String name, Integer number, Long id_user, String email) {
        super();
        this.name = name;
        this.number = number;
        this.id_user = id_user;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}

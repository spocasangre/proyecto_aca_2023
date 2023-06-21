package com.example.project.models.dtos;

import com.example.project.models.entities.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

public class CreateAccountDTO {

    @NotNull
    private String nombre;

    @NotNull
    private Integer telefono;

    @NotNull
    private String correo;

    @NotNull
    private String password;

    @NotNull
    private Long id_rol;

    public CreateAccountDTO(String nombre, Integer telefono, String correo, String password, Long id_rol) {
        super();
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
        this.id_rol = id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId_rol() {
        return id_rol;
    }

    public void setId_rol(Long id_rol) {
        this.id_rol = id_rol;
    }
}

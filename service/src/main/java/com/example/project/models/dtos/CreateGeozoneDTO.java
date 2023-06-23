package com.example.project.models.dtos;

public class CreateGeozoneDTO {
    private String nombre;
    private String desc;

    public CreateGeozoneDTO(String nombre, String desc) {
        this.nombre = nombre;
        this.desc = desc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDesc() {
        return desc;
    }
}

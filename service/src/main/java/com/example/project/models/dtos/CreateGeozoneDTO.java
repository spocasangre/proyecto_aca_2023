package com.example.project.models.dtos;

public class CreateGeozoneDTO {
    private String name;
    private String desc;

    public CreateGeozoneDTO(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}

package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class CreateAdvisoryDTO {

    @NotNull
    private Long type;

    @NotNull
    private String fullname;

    @NotNull
    private Integer number;

    private String image;

    public CreateAdvisoryDTO(Long type, String fullname, Integer number, String image) {
        super();
        this.type = type;
        this.fullname = fullname;
        this.number = number;
        this.image = image;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

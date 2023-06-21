package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class DeleteAdvisorDTO {

    @NotNull
    private Integer id_advisor;

    public DeleteAdvisorDTO(Integer id_advisor) {
        super();
        this.id_advisor = id_advisor;
    }

    public Integer getId_advisor() {
        return id_advisor;
    }

    public void setId_advisor(Integer id_advisor) {
        this.id_advisor = id_advisor;
    }
}

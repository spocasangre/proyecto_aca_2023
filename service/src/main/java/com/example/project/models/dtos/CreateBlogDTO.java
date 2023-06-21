package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class CreateBlogDTO {

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String description;

    private String image;

    @NotNull
    private Long id_user;

    public CreateBlogDTO(String title, String subtitle, String description, String image, Long id_user) {
        super();
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.image = image;
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}

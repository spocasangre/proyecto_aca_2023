package com.example.project.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "asesor")
public class Advisory {

    @Id
    @Column(name = "id_asesor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_asesor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private Integer telefono;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_asesor")
    private AdvisorType advisorType;

    public Advisory(Long id_asesor, String nombre, Integer telefono, AdvisorType advisorType) {
        super();
        this.id_asesor = id_asesor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.advisorType = advisorType;
    }

    public Advisory() {
        super();
    }

    public AdvisorType getAdvisorType() {
        return advisorType;
    }

    public void setAdvisorType(AdvisorType advisorType) {
        this.advisorType = advisorType;
    }

    public Long getId_asesor() {
        return id_asesor;
    }

    public void setId_asesor(Long id_asesor) {
        this.id_asesor = id_asesor;
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
}

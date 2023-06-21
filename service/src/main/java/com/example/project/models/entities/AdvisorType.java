package com.example.project.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tipo_asesor")
public class AdvisorType {

    @Id
    @Column(name = "id_tipo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_asesor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "advisorType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Advisory> advisories;

    public AdvisorType(Long id_tipo_asesor, String nombre, String descripcion) {
        super();
        this.id_tipo_asesor = id_tipo_asesor;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public AdvisorType() {
        super();
    }

    public Long getId_tipo_asesor() {
        return id_tipo_asesor;
    }

    public void setId_tipo_asesor(Long id_tipo_asesor) {
        this.id_tipo_asesor = id_tipo_asesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Advisory> getAdvisories() {
        return advisories;
    }

    public void setAdvisories(List<Advisory> advisories) {
        this.advisories = advisories;
    }
}

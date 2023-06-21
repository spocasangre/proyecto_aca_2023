package com.example.project.models.entities;

import javax.persistence.*;

@Entity(name = "ubicacion")
public class Location {

    @Id
    @Column(name = "id_ubicacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "maps_id")
    private String maps_id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario user;

    public Location(Long id, Double latitud, Double longitud, String maps_id, String titulo, String descripcion, Usuario user) {
        super();
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.maps_id = maps_id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.user = user;
    }

    public Location() {
        super();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaps_id() {
        return maps_id;
    }

    public void setMaps_id(String maps_id) {
        this.maps_id = maps_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}

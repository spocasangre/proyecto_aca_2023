package com.example.project.repositories;

import com.example.project.models.entities.Imagenes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenesRepository extends JpaRepository<Imagenes, Long> {
}

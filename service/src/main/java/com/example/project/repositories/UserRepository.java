package com.example.project.repositories;

import com.example.project.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCodigo(String codigo);

    Boolean existsByCorreo(String correo);

    @Query("SELECT u FROM usuario u WHERE u.rol <> 2")
    List<Usuario> findAllUsers();

}

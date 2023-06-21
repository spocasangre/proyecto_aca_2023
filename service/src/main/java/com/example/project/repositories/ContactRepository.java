package com.example.project.repositories;

import com.example.project.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM contacto c WHERE c.user.id = ?1")
    public List<Contact> getUserContactList(Long id);

}

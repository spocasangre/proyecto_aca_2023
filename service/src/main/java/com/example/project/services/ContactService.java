package com.example.project.services;

import com.example.project.models.entities.Contact;

import java.util.List;

public interface ContactService {
    Void save(Contact contact);
    List<Contact> getUserContactList(Long id);
    Void delete(Contact contact);
    Contact findById(Long id);
}

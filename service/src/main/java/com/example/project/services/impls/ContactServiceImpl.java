/*
 * Copyright 2023 WeGotYou!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.project.services.impls;

import com.example.project.models.entities.Contact;
import com.example.project.repositories.ContactRepository;
import com.example.project.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public Void save(Contact contact) {
        contactRepository.save(contact);
        return null;
    }

    @Override
    public List<Contact> getUserContactList(Long id) {
        return contactRepository.getUserContactList(id);
    }

    @Override
    public Void delete(Contact contact) {
        contactRepository.delete(contact);
        return null;
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id).get();
    }
}

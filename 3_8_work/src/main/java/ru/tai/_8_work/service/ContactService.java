package ru.tai._8_work.service;

import ru.tai._8_work.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);
}

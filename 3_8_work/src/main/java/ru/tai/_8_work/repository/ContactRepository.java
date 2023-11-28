package ru.tai._8_work.repository;

import ru.tai._8_work.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);
}

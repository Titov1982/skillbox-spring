package ru.tai.storage;

import ru.tai.model.Contact;

import java.util.List;

public interface ContactStorage {
    void addContact(Contact contact);

    Contact getContact(String email);

    Contact deleteContact(String email);

    Contact updateContact(String oldEmail, Contact newContact);

    List<Contact> listContacts();
}

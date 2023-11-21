package ru.tai.storage;

import org.springframework.stereotype.Component;
import ru.tai.model.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContactStorageImpl implements ContactStorage{
    private final Map<String, Contact> storage = new HashMap<>();

    public void addContact(Contact contact) {
        storage.put(contact.getEmail(), contact);
    }

    public Contact getContact(String email) {
        return storage.get(email);
    }

    public Contact deleteContact(String email) {
        return storage.remove(email);
    }

    public Contact updateContact(String oldEmail, Contact newContact) {
        Contact oldContact = storage.get(oldEmail);
        storage.remove(oldEmail);
        storage.put(newContact.getEmail(), newContact);
        return storage.get(newContact.getEmail());
    }

    public List<Contact> listContacts() {
        return storage.values().stream().toList();
    }
}

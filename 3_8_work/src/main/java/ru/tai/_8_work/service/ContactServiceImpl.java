package ru.tai._8_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tai._8_work.exception.ContactNotFoundException;
import ru.tai._8_work.model.Contact;
import ru.tai._8_work.repository.ContactRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    @Override
    public List<Contact> findAll() {
        log.debug("ContactServiceImpl->findAll");
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("ContactServiceImpl->findById id= {}", id);
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            return contact;
        }
        throw new ContactNotFoundException("Контакт с ID= " + id + " не найден!");
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("ContactServiceImpl->save contact= {}", contact);
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("ContactServiceImpl->update contact= {}", contact);
        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("ContactServiceImpl->deleteById id= {}", id);
        contactRepository.deleteById(id);
    }
}

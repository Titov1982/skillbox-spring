package ru.tai._8_work.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.tai._8_work.model.Contact;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty("app.contacts-generator.enabled")
public class ContactsGenerator {

    private final ContactService contactService;

    @EventListener(ApplicationStartedEvent.class)
    public void startContactGenerator() {
        log.debug("ContactsGenerator->startContactGenerator");
        Contact contact = contactService.save(new Contact(System.currentTimeMillis(),
                "Иван", "Иванов", "ivanov@mail.com", "+79881234567"));
        log.debug("Сгенерирован тестовый контакт: {}", contact);
    }
}

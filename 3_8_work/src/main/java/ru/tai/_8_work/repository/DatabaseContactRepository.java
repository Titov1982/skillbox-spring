package ru.tai._8_work.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import ru.tai._8_work.exception.ContactNotFoundException;
import ru.tai._8_work.model.Contact;
import ru.tai._8_work.repository.mapper.ContactRowMapper;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("DatabaseContactRepository->findAll");
        String findAllQuery = "SELECT * FROM contacts_schema.contact";
        return jdbcTemplate.query(findAllQuery, new ContactRowMapper<Contact>());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("DatabaseContactRepository->findById id= {}", id);

        String findByIdQuery = "SELECT * FROM contacts_schema.contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(findByIdQuery,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper<>(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("DatabaseContactRepository->save contact= {}", contact);

        contact.setId(System.currentTimeMillis());
        String saveQuery = "INSERT INTO contacts_schema.contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(saveQuery, contact.getId(), contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getPhone());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("DatabaseContactRepository->update contact= {}", contact);

        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            String updateQuery = "UPDATE contacts_schema.contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(updateQuery, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }

        log.warn("Контакт с ID {} не найден!", contact.getId());
        throw new ContactNotFoundException("Контакт для обновления, с ID= " + contact.getId() + "не найден!");
    }

    @Override
    public void deleteById(Long id) {
        log.debug("DatabaseContactRepository->deleteById id= {}", id);

        String deleteByIdQuery = "DELETE FROM contacts_schema.contact WHERE id = ?";
        jdbcTemplate.update(deleteByIdQuery, id);
    }
}

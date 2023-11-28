package ru.tai._8_work.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.tai._8_work.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper<T> implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();

        contact.setId(rs.getLong(Contact.Fields.id));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setEmail(rs.getString(Contact.Fields.email));
        contact.setPhone(rs.getString(Contact.Fields.phone));

        return contact;
    }
}

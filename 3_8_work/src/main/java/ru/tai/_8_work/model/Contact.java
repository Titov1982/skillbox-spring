package ru.tai._8_work.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Contact {

    private Long id;
    @FieldNameConstants.Exclude
    private String firstName;
    @FieldNameConstants.Exclude
    private String lastName;
    private String email;
    private String phone;
}

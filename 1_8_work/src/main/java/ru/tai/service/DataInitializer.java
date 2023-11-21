package ru.tai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.tai.model.Contact;
import ru.tai.storage.ContactStorage;

import java.io.*;

@Component
@Profile("init")
public class DataInitializer {

    @Value("${app.data-file-path}")
    private String dataFilePath;
    @Value("${app.data-file-name}")
    private String dataFileName;

    private final ContactStorage contactStorage;

    public DataInitializer(ContactStorage contactStorage) {
        this.contactStorage = contactStorage;
    }

    public void dataFileLoad() {
        Resource resource = new ClassPathResource(dataFilePath + dataFileName);
        File file = null;
        try {
            file = resource.getFile();
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            while (true) {
                String strData = reader.readLine();
                if (strData == null) {
                    break;
                }
                String[] contactData = strData.split(";");
                contactStorage.addContact(new Contact(contactData[0], contactData[1], contactData[2]));
            }

        } catch (IOException e) {
            System.out.println("В свойствах указан не существующий путь к файлу или файл!");
            System.out.println(e.getMessage());
        }

    }
}

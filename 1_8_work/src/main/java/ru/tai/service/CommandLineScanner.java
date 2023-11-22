package ru.tai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.tai.model.Contact;
import ru.tai.storage.ContactStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineScanner {
    private final ContactStorage contactStorage;
    private final Scanner scanner;

    @Value("${app.data-file-path}")
    private String dataFilePath;
    @Value("${app.data-file-name}")
    private String dataFileName;

    public CommandLineScanner(ContactStorage contactStorage, Scanner scanner) {
        this.contactStorage = contactStorage;
        this.scanner = scanner;
    }

    private void add() {
        System.out.println("Введите информацию о контакте в формате -> \"Иванов Иван Иванович; +79991234567; ivanov@mail.com\":");
        String contactLine = scanner.nextLine();
        String[] contactData = contactLine.split(";");
        if (contactData.length != 3) {
            System.out.println("Вы ввели не верное кол-во параметров с разделителем ';'.\nПопробуйте заного.");
            return;
        }
        for (int i=0; i<contactData.length; i++) {
            contactData[i] = contactData[i].strip();
        }
        contactStorage.addContact(new Contact(contactData[0], contactData[1], contactData[2]));
    }

    private void list(){
        List<Contact> contacts = contactStorage.listContacts();
        if (contacts.isEmpty()) {
            System.out.println("Список контактов пуст.");
        } else {
            System.out.println("Список контактов:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
            System.out.println();
        }
    }

    private void delete() {
        System.out.println("Введите Email удаляемого контакта:");
        String contactLine = scanner.nextLine();
        Contact deletedContact = contactStorage.deleteContact(contactLine);
        if (deletedContact != null){
            System.out.println("Удален конакт: " + deletedContact);
        }
        else {
            System.out.println("Контакт отсутствует в списке.");
        }
    }

    private void save(){
        Resource resource = new ClassPathResource(dataFilePath + dataFileName);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            System.out.println("В свойствах указан не существующий путь к файлу или файл!");
            System.out.println(e.getMessage());
        }
        try (PrintWriter printWriter = new PrintWriter(file)){
            List<Contact> contacts = contactStorage.listContacts();
            for (Contact contact : contacts) {
                printWriter.write(contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail() + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("В свойствах указан не существующий путь к файлу или файл!");
            System.out.println(e.getMessage());
        }
    }

    private void help() {
        System.out.println("Вам доступны следующие команды:");
        System.out.print("list - вывести весь список контактов\n" + "add - добавить новый контакт\n" + "delete - удалить контакт\n" + "save - сохранить список контактов в файле\n" + "help - помощь\n" + "exit - выход из программы\n" + "--------------------------------------------\n");
    }

    public void startCommandLineScanner() {
        System.out.println("Добро пожаловать в справочник контактов!");
        help();
        while (true) {
            System.out.println("Введите команду:");

            // list
            // add
            // delete
            // save
            // exit

            String command = scanner.nextLine();
            if (command.equals("add")) {
                add();
            } else if (command.equals("list")) {
                list();
            } else if (command.equals("delete")) {
                delete();
            } else if (command.equals("save")) {
                save();
            }   else if (command.equals("help")) {
                help();
            } else if (command.equals("exit")) {
                break;
            }else {
                System.out.println("Введена не верная команда! Введите: list, add, delete, save или exit");
            }
        }
    }
}

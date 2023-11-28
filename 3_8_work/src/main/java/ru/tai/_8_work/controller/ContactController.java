package ru.tai._8_work.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tai._8_work.model.Contact;
import ru.tai._8_work.service.ContactService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    @GetMapping("/")
    public String index(Model model) {
        log.debug("ContactController->index");
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }

    @GetMapping("contact/create")
    public String showCreateForm(Model model) {
        log.debug("ContactController->showCreateForm");
        model.addAttribute("actionPath", "/contact/create");
        model.addAttribute("formTitle", "Создание контакта");
        model.addAttribute("contact", new Contact());
        return "contact-form";
    }

    @PostMapping("contact/create")
    public String createContact(@ModelAttribute Contact contact) {
        log.debug("ContactController->createContact");
        contactService.save(contact);
        return "redirect:/";
    }

    @GetMapping("contact/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.debug("ContactServiceImpl->showEditForm id= {}", id);
        model.addAttribute("actionPath", "/contact/edit");
        model.addAttribute("formTitle", "Редактирование контакта");
        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "contact-form";
        }
        return "redirect:/";
    }

    @PostMapping("contact/edit")
    public String editContact(@ModelAttribute Contact contact) {
        log.debug("ContactController->editContact");
        contactService.update(contact);
        return "redirect:/";
    }

    @GetMapping("contact/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        log.debug("ContactServiceImpl->deleteContact id= {}", id);
        contactService.deleteById(id);
        return "redirect:/";
    }
}

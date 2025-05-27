package org.dasher.speed.base.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.dasher.speed.base.domain.Contact;
import org.dasher.speed.base.domain.RoleEnum;
import org.dasher.speed.base.ui.view.events.ContactEventListener;
import org.dasher.speed.taskmanagement.services.CrmService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class ContactForm extends FormLayout {
    Contact currentContact = new Contact();
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<RoleEnum> roleEnum = new ComboBox<>("roleEnum");

    private final CrmService crmService;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private List<ContactEventListener> listeners = new ArrayList<>();

    public ContactForm(CrmService crmService) { 
        addClassName("contact-form");
        setSizeFull();

        binder.bindInstanceFields(this);
        this.crmService = crmService;
        
        roleEnum.setItems(RoleEnum.values()); 
        add(firstName, lastName, email, roleEnum, createButtonsLayout());
    }
    
    public void setContact(Contact contact){
        binder.readBean(contact);
        this.currentContact = contact;
        Notification.show("Contato atual: " + (contact != null ? contact.getId() : "Nenhum contato")); // Debug
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireContactEvent(currentContact, true)); // true para delete
        cancel.addClickListener(event -> clearForm());

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickShortcut(Key.ESCAPE);
        
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave(){
        try {
            binder.writeBean(currentContact);
            saveContact(); 
            fireContactEvent(currentContact, false); 
            clearForm();
            
        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Erro de validação: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Erro inesperado: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    public void addContactListener(ContactEventListener listener) {
        listeners.add(listener);
    }

    private void fireContactEvent(Contact contact, boolean isDelete) {
        for (ContactEventListener listener : listeners) {
            listener.onContactEvent(contact);
        }
        if (isDelete) {
            deleteContact();
            listeners.forEach(listener -> listener.onContactEvent(contact)); // Notifica os listeners
        }
    }

    private void saveContact() {
        crmService.saveContact(currentContact);
    }

    private void deleteContact() {
        if (currentContact != null && currentContact.getId() != null) {
            crmService.deleteContact(currentContact.getId());
            Notification.show("Contato excluído com sucesso!", 3000, Notification.Position.TOP_END);
        } else {
            Notification.show("Nenhum contato selecionado para exclusão.", 3000, Notification.Position.TOP_END);
        }
    }

    private void clearForm() {
        binder.readBean(null); // Limpa o binder
        currentContact = new Contact(); // Reseta o objeto currentContact
    }
}
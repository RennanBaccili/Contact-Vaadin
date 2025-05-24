package org.dasher.speed.base.ui.view;

import java.io.ObjectInputFilter.Status;
import java.util.List;

import org.dasher.speed.base.domain.RoleEnum;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class ContactForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<RoleEnum> status = new ComboBox<RoleEnum>("Status");

 
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ContactForm(List<Status> statuses) {  
        addClassName("contact-form");
        setSizeFull();

        status.setItems(RoleEnum.values()); 
        add(firstName, lastName, email, status, createButtonsLayout());
    }
    
    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickShortcut(Key.ESCAPE);
        
        return new HorizontalLayout(save, delete, cancel);
    }
}

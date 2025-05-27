package org.dasher.speed.base.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.html.H2;
import org.dasher.speed.base.domain.Contact;
import org.dasher.speed.taskmanagement.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route(value = "Users")
public class ListContactsView extends VerticalLayout {
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();
    ContactForm contactForm;
    private final CrmService crmService;


    ListContactsView(CrmService crmService) {
        this.crmService = crmService;
        addClassName("list-view");
        setSizeFull();

        configureForm();
        configureGrid();
    
        add(
            getToolbar(),
            getContent()
        );

        updateList();
    }  
    private Component getContent() {
        var content = new HorizontalLayout(grid, contactForm);
        content.setFlexGrow(4, grid);
        content.setFlexGrow(1, contactForm);
        content.addClassName("content");
        content.setWidth("100%");
        grid.setHeight("600px");
        grid.setWidth("100%");
        return content;
    }
            
    private void configureForm() {
        contactForm = new ContactForm(crmService);
        // Adicionando listener para contatos
        contactForm.addContactListener(contact -> {
            updateList(); // Atualiza a lista após salvar ou deletar
        });
    
        contactForm.setWidth("25em");
        contactForm.setVisible(false);
    }
        
    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        var addContactButton = new Button("Add contact");
        addContactButton.addClickListener(e -> addContact());
        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private void editContact(Contact contact) {
        Contact selectedContact = contact;
        if (selectedContact == null) {
            closeEditor();
        } else {
            contactForm.setContact(selectedContact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void addContact(){
        if(contactForm.isVisible()){
            contactForm.setVisible(false);
        }else {
            contactForm.setVisible(true);
        }
    }

    private void updateList() {
        grid.setItems(crmService.searchContacts(filterText.getValue())); // Atualiza a grid com todos os contatos
    }
}

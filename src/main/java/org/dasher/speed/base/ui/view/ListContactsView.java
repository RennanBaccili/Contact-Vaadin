package org.dasher.speed.base.ui.view;
import java.util.Collections;

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
        loadContacts();
        add(
            getToolbar(),
            getContent()
        );
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
        contactForm = new ContactForm(Collections.emptyList());
        contactForm.setWidth("25em");
        }   
        
        private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        var addContactButton = new Button("Add contact");

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void loadContacts(){
        var contacts = crmService.getAllContacts();
        grid.setItems(contacts);
    }
            /**
     * Navigates to the main view.
     */
    public static void showMainView() {
    }
}

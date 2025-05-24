package org.dasher.speed.base.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Person")
public class Contact { // Parameterized with Long

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Isso agora está OK
    private String firstName;
    private String lastName;
    private String email;
    private RoleEnum roleEnum;

    public Contact(String firstName, String lastName, String email, RoleEnum roleEnum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleEnum = roleEnum;
    }

    public Contact() {
        super();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRoleEnum() { 
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) { 
        this.roleEnum = roleEnum;
    }
}
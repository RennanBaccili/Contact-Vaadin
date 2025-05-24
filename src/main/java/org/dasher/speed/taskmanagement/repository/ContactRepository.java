package org.dasher.speed.taskmanagement.repository;

import java.util.List;

import org.dasher.speed.base.domain.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@EnableJpaRepositories
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c" +
    " where lower(c.firstName) like lower(concat('%', :searchTerm, '%'))" + 
    " or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))") 
    List<Contact> search(@Param("searchTerm") String filterText);
}

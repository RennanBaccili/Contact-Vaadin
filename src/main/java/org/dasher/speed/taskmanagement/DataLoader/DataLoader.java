package org.dasher.speed.taskmanagement.DataLoader;

import org.dasher.speed.base.domain.Contact;
import org.dasher.speed.base.domain.RoleEnum;
import org.dasher.speed.taskmanagement.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Este DataLoader será executado apenas no perfil "dev"
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void run(String... args) throws Exception {
        var contact1 = new Contact("John", "Doe", "john.doe@example.com", RoleEnum.Patient);
        var contact2 = new Contact("Jane", "Doe", "jane.doe@example.com", RoleEnum.Doctor);

        // Inserindo dados automaticamente
        contactRepository.save(contact1);
        contactRepository.save(contact2);
    }
}
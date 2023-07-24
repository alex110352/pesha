package com.example.pesha.initializer;

import com.example.pesha.dao.entity.Authority;
import com.example.pesha.dao.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AuthorityDataInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityDataInitializer(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) {
        createAuthorityIfNotExists( "admin");
        createAuthorityIfNotExists( "normal");
        createAuthorityIfNotExists( "ROLE_manager");
        createAuthorityIfNotExists( "ROLE_employee");
    }

    private void createAuthorityIfNotExists(String authorityName) {
        Authority authority = authorityRepository.findByAuthority(authorityName);
        if (authority == null) {
            authority = new Authority();
            authority.setAuthority(authorityName);
            authorityRepository.save(authority);
        }
    }
}

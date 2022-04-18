package com.hexagonal.infrastructure.config;

import com.hexagonal.application.repository.PersonaRepository;
import com.hexagonal.application.service.PersonaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonaService personaService(PersonaRepository personaRepository){
        return new PersonaService(personaRepository);
    }
}

package com.hexagonal.application.service;

import com.hexagonal.application.repository.PersonaRepository;
import com.hexagonal.domain.Persona;

public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona findPersonaById(Long id) {
        return personaRepository.getById(id);
    }

    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }
}

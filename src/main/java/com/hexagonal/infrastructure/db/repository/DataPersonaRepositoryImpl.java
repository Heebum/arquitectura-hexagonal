package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.db.mapper.PersonaEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPersonaRepositoryImpl implements PersonaRepository {

    @Autowired
    DataPersonaRepository dataPersonaRepository;
    @Autowired
    PersonaEntityMapper personaEntityMapper;


    @Override
    public Persona getById(Long id) {
        return personaEntityMapper.toDomaindb(dataPersonaRepository.findById(id).orElseThrow( ()-> new PersonaNotFoundException(id)));
    }
    @Override
    public Persona save(Persona persona) {
        return personaEntityMapper.toDomaindb(dataPersonaRepository.save(personaEntityMapper.toDb(persona)));
    }
}

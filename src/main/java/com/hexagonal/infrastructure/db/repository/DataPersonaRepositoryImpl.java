package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.db.entity.PersonaEntity;
import com.hexagonal.infrastructure.db.mapper.PersonaEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DataPersonaRepositoryImpl implements PersonaRepository {

    @Autowired
    DataPersonaRepository dataPersonaRepository;
    @Autowired
    PersonaEntityMapper personaEntityMapper;

    private static final String PERSONA_NOT_FOUND = "Persona con id= %s no existe";


    @Override
    public Persona getById(Long id) {
        PersonaEntity data = dataPersonaRepository.findById(id).orElseThrow(()-> new PersonaNotFoundException(String.format(PERSONA_NOT_FOUND,id)));
        return personaEntityMapper.toDomaindb(data);
    }
    @Override
    public Persona save(Persona persona) {
        return personaEntityMapper.toDomaindb(dataPersonaRepository.save(personaEntityMapper.toDb(persona)));
    }

    @Override
    public List<Persona> findAllPersonas() {
        List<PersonaEntity> personaList = new ArrayList<PersonaEntity>();
        dataPersonaRepository.findAll().forEach(personaList::add);
        return personaEntityMapper.toAllDb(personaList);
    }

    @Override
    public Persona update(Persona persona, Long id) {
        PersonaEntity persona1 = (dataPersonaRepository.findById(id).orElseThrow( ()-> new PersonaNotFoundException(String.format(PERSONA_NOT_FOUND,id))));
        persona1.setNombre(persona.getNombre());
        persona1.setApellido(persona.getApellido());
        persona1.setEdad(persona.getEdad());
        persona1.setCiudad(persona.getCiudad());

        return personaEntityMapper.toDomaindb(dataPersonaRepository.save(persona1));
    }

    @Override
    public Persona delete(Long id) {
        PersonaEntity persona = dataPersonaRepository.findById(id).orElseThrow( ()-> new PersonaNotFoundException(String.format(PERSONA_NOT_FOUND,id)));
        dataPersonaRepository.deleteById(id);
        return personaEntityMapper.toDomaindb(persona);
    }
}

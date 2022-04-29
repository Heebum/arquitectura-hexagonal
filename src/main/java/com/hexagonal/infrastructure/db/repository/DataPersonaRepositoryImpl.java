package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.db.entity.PersonaEntity;
import com.hexagonal.infrastructure.db.mapper.PersonaEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Persona> findAllPersonas() {
        List<PersonaEntity> personaList = new ArrayList<PersonaEntity>();
        dataPersonaRepository.findAll().forEach(personaList::add);
        return personaEntityMapper.toAllDb(personaList);
    }

    @Override
    public Persona update(Persona persona, Long id) {
        PersonaEntity persona1 = (dataPersonaRepository.findById(id).orElseThrow( ()-> new PersonaNotFoundException(id)));
//        if (persona1.getId()<0){
//            throw new PersonaNotFoundException(id);
//        }
//        PersonaEntity persona2 = persona1;
        persona1.setNombre(persona.getNombre());
        persona1.setApellido(persona.getApellido());
        persona1.setEdad(persona.getEdad());
        persona1.setCiudad(persona.getCiudad());

        return personaEntityMapper.toDomaindb(dataPersonaRepository.save(persona1));
    }

    @Override
    public Persona delete(Long id) {
        Optional persona1 = dataPersonaRepository.findById(id);
        if (persona1.isEmpty()){
            throw new PersonaNotFoundException(id);
        }
        PersonaEntity persona = dataPersonaRepository.findById(id).get();
        dataPersonaRepository.deleteById(id);
        return personaEntityMapper.toDomaindb(persona);
    }
}

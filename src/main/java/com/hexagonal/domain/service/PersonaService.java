package com.hexagonal.domain.service;

import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.domain.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona findPersonaById(Long id) {
        return personaRepository.getById(id);
    }

    public List<Persona> findPersonaAll(){
        List<Persona> personaList = new ArrayList<Persona>();
        personaRepository.findAllPersonas().forEach(personaList::add);
        if (personaList.isEmpty()){
            throw new RuntimeException("No existen personas");
        }
        return personaList;
    }

    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona update(Persona persona, Long id){
        Optional<Persona> persona1 = Optional.ofNullable(personaRepository.getById(id));
//        if (persona1.isEmpty()){
//            throw new PersonaNotFoundException(id);
//        }
        Persona personaUpdt =persona1.get();
        personaUpdt.setNombre(persona.getNombre());
        personaUpdt.setApellido(persona.getApellido());
        personaUpdt.setEdad(persona.getEdad());
        personaUpdt.setCiudad(persona.getCiudad());

        personaRepository.update(personaUpdt,id);
        return personaUpdt;
    }

    public Persona delete(Long id){
        Optional<Persona> persona1 = Optional.ofNullable(personaRepository.getById(id));
        if (persona1.isEmpty()){
            throw new PersonaNotFoundException(id);
        }
        return personaRepository.delete(id);
    }

}

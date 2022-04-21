package com.hexagonal.domain.repository;

import com.hexagonal.domain.Persona;

public interface PersonaRepository {
    Persona getById(Long id);
    Persona save(Persona persona);
}

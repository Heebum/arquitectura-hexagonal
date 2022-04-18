package com.hexagonal.domain;


import com.hexagonal.application.service.exceptions.PersonaNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {
    @Test
    public void personaToString(){
        Persona persona = Persona.builder().nombre("stefanie").apellido("cortina").build();
        Assertions.assertNotNull(persona.toString());
        Assertions.assertEquals("stefanie",persona.getNombre());
    }

}
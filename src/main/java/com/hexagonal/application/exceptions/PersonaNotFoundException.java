package com.hexagonal.application.exceptions;

public class PersonaNotFoundException extends RuntimeException{

    public PersonaNotFoundException(Long id){
        super(String.format("Persona con id= %s no existe",id));
    }
}

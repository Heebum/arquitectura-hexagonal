package com.hexagonal.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonaNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PersonaNotFoundException(String msg){
        super(msg);
    }
}

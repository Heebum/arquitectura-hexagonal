package com.hexagonal.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImagenNotFoundException extends RuntimeException{

    public ImagenNotFoundException(String _id){
        super(String.format("Imagen con id= %s no existe",_id));
    }
}

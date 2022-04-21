package com.hexagonal.application.exceptions;

public class ImagenNotFoundException extends RuntimeException{

    public ImagenNotFoundException(String _id){
        super(String.format("Imagen con id= %s no existe",_id));
    }
}

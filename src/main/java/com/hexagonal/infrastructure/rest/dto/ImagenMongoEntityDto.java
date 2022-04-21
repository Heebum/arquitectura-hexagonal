package com.hexagonal.infrastructure.rest.dto;

import lombok.Data;

@Data
public class ImagenMongoEntityDto {

    private String foto;
    private PersonaEntityDto persona;
}

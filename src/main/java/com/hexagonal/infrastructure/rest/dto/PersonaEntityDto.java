package com.hexagonal.infrastructure.rest.dto;

import lombok.Data;

@Data
public class PersonaEntityDto {
    private final String nombre;
    private final String apellido;
    private final String edad;
    private final String ciudad;
}

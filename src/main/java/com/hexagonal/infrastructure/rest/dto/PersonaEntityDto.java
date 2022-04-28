package com.hexagonal.infrastructure.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PersonaEntityDto {
    private final String nombre;
    private final String apellido;
    private final String edad;
    private final String ciudad;

}

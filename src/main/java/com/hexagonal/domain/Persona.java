package com.hexagonal.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Persona {

    private Long id;
    private String nombre;
    private String apellido;
    private String edad;
    private String ciudad;
}

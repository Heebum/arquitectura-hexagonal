package com.hexagonal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    private Long id;
    private String nombre;
    private String apellido;
    private String edad;
    private String ciudad;
}

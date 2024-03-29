package com.hexagonal.domain;

import lombok.*;

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

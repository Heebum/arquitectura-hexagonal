package com.hexagonal.infrastructure.rest.mapper;

import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.rest.dto.PersonaEntityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    PersonaEntityDto toDto(Persona persona);
    Persona toDomain(PersonaEntityDto personaEntityDto);
    List<PersonaEntityDto> toAllDto(List<Persona> personaList);
}

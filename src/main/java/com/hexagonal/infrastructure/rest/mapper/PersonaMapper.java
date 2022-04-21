package com.hexagonal.infrastructure.rest.mapper;

import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.rest.dto.PersonaEntityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    PersonaEntityDto toDto(Persona persona);
    Persona toDomain(PersonaEntityDto personaEntityDto);
}

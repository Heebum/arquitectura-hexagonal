package com.hexagonal.infrastructure.rest.mapper;

import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.rest.dto.PersonaEntityDto;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    PersonaEntityDto toDto(Persona persona);
    Persona toDomain(PersonaEntityDto personaEntityDto);
}

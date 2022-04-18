package com.hexagonal.infrastructure.db.mapper;


import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.db.entity.PersonaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonaEntityMapper {

    Persona toDomaindb(PersonaEntity personaEntity);
    PersonaEntity toDb(Persona persona);
}

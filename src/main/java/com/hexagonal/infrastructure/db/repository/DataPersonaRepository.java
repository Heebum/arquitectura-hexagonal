package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.infrastructure.db.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPersonaRepository extends JpaRepository<PersonaEntity, Long> {

}

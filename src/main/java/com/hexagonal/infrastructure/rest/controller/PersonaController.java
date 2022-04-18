package com.hexagonal.infrastructure.rest.controller;

import com.hexagonal.application.service.PersonaService;
import com.hexagonal.domain.Persona;
import com.hexagonal.infrastructure.rest.dto.PersonaEntityDto;
import com.hexagonal.infrastructure.rest.mapper.PersonaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RequestMapping("/api")
@RestController
public class PersonaController {

    @Autowired
    PersonaService personaService;
    @Autowired
    PersonaMapper personaMapper;

    @ApiOperation(value="Crear nueva Persona", notes="Proporciona una operación para crear un nuevo objeto Persona y devolver su identificador")
    @ApiResponses(value= {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
    })
    @PostMapping(value = "/persona", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> create(@RequestBody PersonaEntityDto personaEntityDto){
        try {
            return new ResponseEntity<>(personaMapper.toDto(personaService.save(personaMapper.toDomain(personaEntityDto))),HttpStatus.CREATED);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }

    }
    @ApiOperation(value="Obtener persona por id", notes="Proporciona una operación para obtener un objeto Persona por su identificador")
    @ApiResponses(value={
            @ApiResponse(code=200, message="OK", response= Persona.class),
            @ApiResponse(code=404, message="Not Found", response=String.class),
            @ApiResponse(code=500, message="Internal Server Error", response=String.class)
    })
    @GetMapping(value = "/persona/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> getPersonaById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(personaMapper.toDto(personaService.findPersonaById(id)), HttpStatus.OK);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
        }

    }
}

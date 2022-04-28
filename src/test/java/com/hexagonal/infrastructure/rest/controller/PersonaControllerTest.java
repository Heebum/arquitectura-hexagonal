package com.hexagonal.infrastructure.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.Persona;
import com.hexagonal.domain.service.PersonaService;
import com.hexagonal.infrastructure.rest.dto.PersonaEntityDto;
import com.hexagonal.infrastructure.rest.mapper.PersonaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PersonaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    PersonaService personaService;
    @Mock
    PersonaMapper personaMapper;

    @InjectMocks
    private PersonaController personaController;

    ObjectMapper objectMapper;

    private Persona persona = new Persona();
    private PersonaEntityDto personaDto = new PersonaEntityDto("frank","castro","29","malambo");

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(personaController).build();

        persona.setNombre("Stie");
        persona.setApellido("cormar");
        persona.setEdad("30");
        persona.setCiudad("soledad");
        persona.setId(1L);

    }


    @Test
    void createPersonTest() throws Exception {
//        PersonaEntityDto dto = new PersonaEntityDto("frank","castro","29","malambo");
//
        mvc.perform(post("/api/persona").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(personaDto))).
                andExpect(status().isCreated());
        verify(personaService,times(1)).save(any());
    }

    @Test
    void getPersonaById() throws Exception {
        when(personaService.findPersonaById(persona.getId())).thenReturn(persona);
        mvc.perform(get("/api/persona/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(persona)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void findAllPerson() throws Exception{
        when(personaService.findPersonaAll()).thenReturn(Arrays.asList(persona));
        mvc.perform(get("/api/persona").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(Arrays.asList(persona)))).
                andDo(print());
        verify(personaService).findPersonaAll();
        verify(personaService,times(1)).findPersonaAll();
    }

    @Test
    void updatePerson() throws Exception{
        Persona person = new Persona();
        person.setId(3L);
        person.setNombre("frank");
        person.setApellido("castro");
        person.setCiudad("malambo");
        person.setEdad("32");

        mvc.perform(put("/api/persona/3").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(person))).
                andExpect(status().isOk());
        verify(personaService,times(1)).update(any(),eq(3L));
    }

    @Test
    void deletePerson() throws Exception{
        when(personaService.delete(persona.getId())).thenReturn(persona);
        mvc.perform(delete("/api/persona/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(persona)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void findPersonaAllException(){
//        Exception exception = assertThrows(RuntimeException.class, ()->{
//            personaService.findPersonaAll();
//        }, ()-> "Error "+RuntimeException.class.getSimpleName());
//        assertEquals("No existen personas" , exception.getMessage());
//        assertNotNull(exception);


//        PersonaController dictMock = mock(PersonaController.class);
        doThrow(new RuntimeException("No existen personas")).when(personaService).findPersonaAll().isEmpty();
//        when(personaService.findPersonaAll()).thenThrow( new PersonaNotFoundException(persona.getId()));

//         personaController.findAllPerson("No existen personas");
    }
}
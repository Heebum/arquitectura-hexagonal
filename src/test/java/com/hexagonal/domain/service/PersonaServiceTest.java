package com.hexagonal.domain.service;

import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.domain.Persona;
import com.hexagonal.domain.repository.PersonaRepository;
import lombok.val;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;
    @InjectMocks
    private PersonaService personaService;

    private Persona persona = new Persona();
    private Persona persona2 = new Persona(2L,"alice","marquez","4","soledad");

    @BeforeEach
    void setUp() {

//        persona = new Persona();
        persona.setNombre("Stie");
        persona.setApellido("cormar");
        persona.setEdad("30");
        persona.setCiudad("soledad");
        persona.setId(1L);
    }

    @Test
    void findPersonaAll(){
        when(personaRepository.findAllPersonas()).thenReturn(Arrays.asList(persona));
        assertNotNull(personaService.findPersonaAll());

        List<Persona> personas = personaService.findPersonaAll();

        assertFalse(personas.isEmpty());
        assertEquals(1, personas.size());
        assertFalse(personas.contains(persona2));
        assertEquals(1L, personas.get(0).getId());
        assertEquals("Stie", personas.get(0).getNombre());
        MatcherAssert.assertThat(personas.get(0).getNombre(), is(equalTo("Stie")));

        verify(personaRepository,atLeastOnce()).findAllPersonas();
        verify(personaRepository,times(2)).findAllPersonas();
    }
    @Test
    void findPersonaAllException(){
        Exception exception = assertThrows(RuntimeException.class, ()->{
            personaService.findPersonaAll();
        }, ()-> "Error "+RuntimeException.class.getSimpleName());
        assertEquals("No existen personas" , exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void save(){
        Persona persona = new Persona(5L,"ana","marquez","43","puerto colombia");// new Persona(null, "ana","marquez","43","puerto colombia");
        when(personaRepository.save(any())).then(invocation ->{
            Persona c = invocation.getArgument(0);
            c.setId(5L);
            return c;
        });

//        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        assertNotNull(personaService.save(persona));
        assertEquals("ana", persona.getNombre());
        assertEquals(5, persona.getId());
        assertEquals("puerto colombia", persona.getCiudad());

        verify(personaRepository).save(any());
        verify(personaRepository,times(1)).save(any());
    }

    @Test
    void findPersonaByIdTest(){
        when(personaRepository.getById(anyLong())).thenReturn(persona);

        Optional<Persona> actual = Optional.ofNullable(personaService.findPersonaById(1L));

        assertEquals(1L, actual.orElseThrow().getId());
        assertEquals("Stie", actual.orElseThrow().getNombre());
        verify(personaRepository).getById(anyLong());
    }
    @Test
    void delete() {
        when(personaRepository.getById(anyLong())).thenReturn(persona);
        personaService.delete(persona.getId());
//        assertThrows(PersonaNotFoundException.class, () -> personaService.delete(2L));
        verify(personaRepository).delete(persona.getId());

    }

    @Test
    void deleteException(){
        Exception exception = assertThrows(PersonaNotFoundException.class, ()->{
            personaService.delete(2L);
        }, ()-> "Error "+PersonaNotFoundException.class.getSimpleName());
        assertEquals("Persona con id= 2 no existe" , exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void updatePersonaTest() {
//        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        when(personaRepository.getById(anyLong())).thenReturn(persona);
        System.out.println("persona "+persona);
        Optional<Persona> actual = Optional.ofNullable(personaService.findPersonaById(1L));
        System.out.println(actual);
        Persona person = actual.orElseThrow();
        person.setNombre("alice");
        person.setApellido(actual.get().getApellido());
        person.setEdad(actual.get().getEdad());
        person.setCiudad(actual.get().getCiudad());

        Persona saved = personaService.update(persona,1L);

        assertThat(saved.getNombre()).isNotNull();
        assertNotNull(persona.getId());
        assertEquals(1L, persona.getId());
        assertEquals("alice", persona.getNombre());

        verify(personaRepository).update(any(Persona.class),eq(1L));
    }

    @Test
    void updateException(){
        Exception exception = assertThrows(PersonaNotFoundException.class, ()->{
            personaService.update(persona,2L);
        }, ()-> "Error "+PersonaNotFoundException.class.getSimpleName());
        assertEquals("Persona con id= 2 no existe" , exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    public void testToString() {
        EqualsVerifier.forClass(Persona.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
    @Test
    public void toString_success() {
        //when(personaRepository.getById(anyLong())).thenReturn(persona);
        assertThat(persona.getApellido().toString()).isEqualTo("cormar");
    }

}
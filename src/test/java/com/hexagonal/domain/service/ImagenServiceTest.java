package com.hexagonal.domain.service;

import com.google.common.hash.HashCode;
import com.hexagonal.application.exceptions.ImagenNotFoundException;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.domain.Persona;
import com.hexagonal.domain.repository.ImagenRepository;
import com.hexagonal.domain.repository.PersonaRepository;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.bson.types.ObjectId;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ImagenServiceTest {

    @Mock
    private PersonaRepository personaRepository;
    @Mock
    private ImagenRepository imagenRepository;
    @InjectMocks
    private ImagenService imagenService;

    private Persona persona = new Persona();
    private ImagenMongo imagenMongo = new ImagenMongo();
    private ImagenMongo imagenMongo2 = new ImagenMongo("12a","imagen3",persona);

    @BeforeEach
    void setUp() {

//        persona = new Persona();
        persona.setNombre("Stie");
        persona.setApellido("cormar");
        persona.setEdad("30");
        persona.setCiudad("soledad");
        persona.setId(1L);

//        imagenMongo = new ImagenMongo();
        imagenMongo.setFoto("imagen2.jpg");
        imagenMongo.setPersona(persona);
        imagenMongo.set_id("12as");
    }

    @Test
    void findImagenById() {
        ObjectId id = ObjectId.get();
        when(imagenRepository.getById(anyString())).thenReturn(imagenMongo);

        Optional<ImagenMongo> actual = Optional.ofNullable(imagenService.findImagenById("12as"));

        assertEquals("12as", actual.orElseThrow().get_id());
        assertEquals("imagen2.jpg", actual.orElseThrow().getFoto());
        verify(imagenRepository).getById(anyString());
        verify(imagenRepository, atLeastOnce()).getById(anyString());
    }

    @Test
    void findImagenAll() {
        when(imagenRepository.findAllImages()).thenReturn(Arrays.asList(imagenMongo));

        List<ImagenMongo> imagens = imagenService.findImagenAll();

        assertNotNull(imagenService.findImagenAll());
        assertFalse(imagens.isEmpty());
        assertEquals(1, imagens.size());
        assertTrue(imagens.contains(imagenMongo));
        assertEquals("12as", imagens.get(0).get_id());
        assertEquals("imagen2.jpg", imagens.get(0).getFoto());
        MatcherAssert.assertThat(imagens.get(0).getFoto(), is(equalTo("imagen2.jpg")));

        verify(imagenRepository,atLeastOnce()).findAllImages();
        verify(imagenRepository,times(2)).findAllImages();
    }
    @Test
    void findImagenAllException(){
        Exception exception = assertThrows(RuntimeException.class, ()->{
            imagenService.findImagenAll();
        }, ()-> "Error "+RuntimeException.class.getSimpleName());
        assertEquals("No existen imagenes" , exception.getMessage());
    }

    @Test
    void save() throws IOException {
        when(personaRepository.getById(anyLong())).thenReturn(persona);
        when(imagenRepository.save(any(ImagenMongo.class))).thenReturn(imagenMongo);

        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        assertNotNull(imagenService.save(storedImage, String.valueOf(persona.getId())));
        assertEquals("12as", imagenMongo.get_id());
        assertEquals(1, imagenMongo.getPersona().getId());

        verify(imagenRepository).save(any());
        verify(imagenRepository,times(1)).save(any());
    }
    @Test
    void saveException() throws IOException{
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        PersonaNotFoundException exception = assertThrows(PersonaNotFoundException.class, ()->{
            imagenService.save(storedImage,"26");//throw new PersonaNotFoundException(6L);
        }, ()-> "Error "+PersonaNotFoundException.class.getSimpleName());
        System.out.println(exception.getMessage());

        assertEquals("Persona con id= 26 no existe" , exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void update() throws IOException {
        when(personaRepository.getById(anyLong())).thenReturn(persona);
        when(imagenRepository.getById(anyString())).thenReturn(imagenMongo);
        System.out.println(imagenMongo);
        Optional<ImagenMongo> actual = Optional.ofNullable(imagenService.findImagenById("2"));
        System.out.println(actual);
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        ImagenMongo image = actual.orElseThrow();
        image.setFoto(String.valueOf(storedImage));
        image.setPersona(persona);

        ImagenMongo saved = imagenService.update(storedImage,String.valueOf(persona.getId()),"12as");
        System.out.println(saved);
        assertThat(saved.getFoto()).isNotNull();
        assertTrue(image.get_id().equals(imagenMongo.get_id()));

        verify(imagenRepository,atLeastOnce()).update(any(),anyString(),any());
        verify(imagenRepository,times(1)).update(any(),anyString(),any());

    }
    @Test
    void updateException() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        Exception exception = assertThrows(ImagenNotFoundException.class, ()->{
            imagenService.update(storedImage,"26","2");
        }, ()-> "Error "+ImagenNotFoundException.class.getSimpleName());
        assertEquals("Imagen con id= 2 no existe" , exception.getMessage());

    }
    @Test
    void updateExceptionPerson() throws IOException{
        when(imagenRepository.getById(anyString())).thenReturn(imagenMongo);
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        PersonaNotFoundException exception = assertThrows(PersonaNotFoundException.class, ()->{
            imagenService.update(storedImage, String.valueOf(36L),"2");
        }, ()-> "Error "+PersonaNotFoundException.class.getSimpleName());
        System.out.println(exception.getMessage());

        assertEquals("Persona con id= 36 no existe" , exception.getMessage());
        assertNotNull(exception);
    }
    @Test
    void delete() {
        when(imagenRepository.getById(anyString())).thenReturn(imagenMongo);
        imagenService.delete(imagenMongo.get_id());

        verify(imagenRepository).delete(imagenMongo.get_id());
    }
    @Test
    void deleteException(){
        Exception exception = assertThrows(ImagenNotFoundException.class, ()->{
            imagenService.delete("2");
        }, ()-> "Error "+ImagenNotFoundException.class.getSimpleName());
        assertEquals("Imagen con id= 2 no existe" , exception.getMessage());
    }

    @Test
    public void testToString() {
//        EqualsVerifier.forClass(ImagenMongo.class)
//                //.withRedefinedSuperclass()
//                .suppress(Warning.ALL_NONFINAL_FIELDS_SHOULD_BE_USED)
//                .verify();

        EqualsVerifier.forClass(ImagenMongo.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
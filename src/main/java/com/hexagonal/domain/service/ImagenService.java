package com.hexagonal.domain.service;

import com.hexagonal.application.exceptions.ImagenNotFoundException;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.repository.ImagenRepository;
import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.domain.Persona;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ImagenService {

    private final PersonaRepository personaRepository;
    private final ImagenRepository imagenRepository;

    public ImagenService(PersonaRepository personaRepository, ImagenRepository imagenRepository) {
        this.personaRepository = personaRepository;
        this.imagenRepository = imagenRepository;
    }

    public ImagenMongo findImagenById(String _id){
        return imagenRepository.getById(_id);
    }

    public List<ImagenMongo> findImagenAll(){
        List<ImagenMongo> listar = new ArrayList<ImagenMongo>();
        imagenRepository.findAllImages().forEach(listar::add);
        if (listar.isEmpty()){
            throw new RuntimeException("No existen imagenes");
        }
        return listar;
    }
    public ImagenMongo save(MultipartFile file, String fk_persona) throws IOException {
        ImagenMongo image = new ImagenMongo();
        byte[] fileContent = file.getBytes();
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        image.setFoto(encodedString);
        Persona persona = personaRepository.getById(Long.valueOf(fk_persona));
        if (persona.getId() < 0){
            throw new PersonaNotFoundException(persona.getId());
        }
        image.setPersona(persona);

        return imagenRepository.save(image);
    }
}

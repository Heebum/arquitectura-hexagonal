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
import java.util.Optional;

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

        Optional<Persona> persona = Optional.ofNullable(personaRepository.getById(Long.valueOf(fk_persona)));
        if (persona.isEmpty()){
            throw new PersonaNotFoundException(Long.valueOf(fk_persona));
        }
        image.setPersona(persona.orElseThrow());

        return imagenRepository.save(image);
    }
    public ImagenMongo update(MultipartFile file, String fk_persona, String _id) throws IOException {

        Optional<ImagenMongo> imagenMongo = Optional.ofNullable(imagenRepository.getById(_id));
        if (imagenMongo.isEmpty()){
            throw new ImagenNotFoundException(_id);
        }
        ImagenMongo update = imagenMongo.orElseThrow();
        byte[] fileContent = file.getBytes();
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        update.setFoto(encodedString);
        Optional<Persona> persona1 = Optional.ofNullable(personaRepository.getById(Long.valueOf(fk_persona)));
        if (persona1.isEmpty()){
            throw new PersonaNotFoundException(Long.valueOf(fk_persona));
        }
        update.setPersona(persona1.orElseThrow());
        imagenRepository.update(file,fk_persona,_id);
        return update;
    }
    public ImagenMongo delete(String _id){
        Optional<ImagenMongo> imagenMongo = Optional.ofNullable(imagenRepository.getById(_id));
        if (imagenMongo.isEmpty()){
            throw new ImagenNotFoundException(_id);
        }

        return imagenRepository.delete(_id);
    }
}

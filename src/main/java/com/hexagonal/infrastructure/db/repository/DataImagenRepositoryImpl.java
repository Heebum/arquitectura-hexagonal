package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.application.exceptions.ImagenNotFoundException;
import com.hexagonal.application.exceptions.PersonaNotFoundException;
import com.hexagonal.domain.repository.ImagenRepository;
import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.infrastructure.db.entity.ImagenMongoEntity;
import com.hexagonal.infrastructure.db.entity.PersonaEntity;
import com.hexagonal.infrastructure.db.mapper.ImagenEntityMapper;
import com.hexagonal.infrastructure.db.mapper.PersonaEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service
public class DataImagenRepositoryImpl implements ImagenRepository{

    @Autowired
    DataImagenRepository dataImagenRepository;
    @Autowired
    DataPersonaRepository dataPersonaRepository;
    @Autowired
    ImagenEntityMapper imagenEntityMapper;
    @Autowired
    PersonaEntityMapper personaEntityMapper;

    @Override
    public ImagenMongo getById(String _id) {
        return imagenEntityMapper.toDomainDb(dataImagenRepository.findById(_id).orElseThrow( ()-> new ImagenNotFoundException(_id)));
    }

    @Override
    public ImagenMongo save(ImagenMongo imagenMongo) {
        return imagenEntityMapper.toDomainDb(dataImagenRepository.save(imagenEntityMapper.toDb(imagenMongo)));
    }

    @Override
    public Collection<ImagenMongo> findAllImages() {
        List<ImagenMongoEntity> listar = new ArrayList<ImagenMongoEntity>();
        dataImagenRepository.findAll().forEach(listar::add);
        return imagenEntityMapper.toAllDb(listar);
    }

    @Override
    public ImagenMongo update(MultipartFile file, String fk_persona, String id) throws IOException {
        ImagenMongoEntity image = dataImagenRepository.findById(id).orElseThrow( ()-> new ImagenNotFoundException(id));
        ImagenMongoEntity update = image;
        byte[] fileContent = file.getBytes();
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        image.setFoto(encodedString);

        PersonaEntity persona1 = dataPersonaRepository.findById(Long.valueOf(fk_persona)).orElseThrow( ()-> new PersonaNotFoundException(Long.valueOf(fk_persona)));
        image.setPersona(personaEntityMapper.toDomaindb(persona1));

        return imagenEntityMapper.toDomainDb(dataImagenRepository.save(image));
    }

    @Override
    public ImagenMongo delete(String _id) {
        ImagenMongoEntity image = dataImagenRepository.findById(_id).orElseThrow( ()-> new ImagenNotFoundException(_id));
        dataImagenRepository.deleteById(_id);
        return imagenEntityMapper.toDomainDb(image);
    }
}

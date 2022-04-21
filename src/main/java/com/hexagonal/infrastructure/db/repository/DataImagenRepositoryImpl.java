package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.application.exceptions.ImagenNotFoundException;
import com.hexagonal.domain.repository.ImagenRepository;
import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.infrastructure.db.entity.ImagenMongoEntity;
import com.hexagonal.infrastructure.db.mapper.ImagenEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DataImagenRepositoryImpl implements ImagenRepository {

    @Autowired
    DataImagenRepository dataImagenRepository;
    @Autowired
    ImagenEntityMapper imagenEntityMapper;

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
        return imagenEntityMapper.toAllDto(listar);
    }
}

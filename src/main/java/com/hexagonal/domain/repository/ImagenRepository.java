package com.hexagonal.domain.repository;

import com.hexagonal.domain.ImagenMongo;

import java.util.Collection;
import java.util.List;

public interface ImagenRepository {
    ImagenMongo getById(String _id);
    ImagenMongo save(ImagenMongo imagenMongo);
    Collection<ImagenMongo> findAllImages();
}

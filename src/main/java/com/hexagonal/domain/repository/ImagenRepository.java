package com.hexagonal.domain.repository;

import com.hexagonal.domain.ImagenMongo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ImagenRepository {
    ImagenMongo getById(String _id);
    ImagenMongo save(ImagenMongo imagenMongo);
    Collection<ImagenMongo> findAllImages();
    ImagenMongo update(MultipartFile file, String fk_persona, String id) throws IOException;
    ImagenMongo delete(String _id);
}

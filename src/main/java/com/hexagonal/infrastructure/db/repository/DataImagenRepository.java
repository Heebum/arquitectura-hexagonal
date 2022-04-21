package com.hexagonal.infrastructure.db.repository;

import com.hexagonal.infrastructure.db.entity.ImagenMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataImagenRepository extends MongoRepository<ImagenMongoEntity,String> {

}

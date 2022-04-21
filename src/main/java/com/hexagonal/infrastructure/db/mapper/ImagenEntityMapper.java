package com.hexagonal.infrastructure.db.mapper;

import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.infrastructure.db.entity.ImagenMongoEntity;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ImagenEntityMapper {
    ImagenMongo toDomainDb(ImagenMongoEntity imagenMongoEntity);
    ImagenMongoEntity toDb(ImagenMongo imagenMongo);
    Collection<ImagenMongo> toAllDto(List<ImagenMongoEntity> imagenMongoEntity);
}

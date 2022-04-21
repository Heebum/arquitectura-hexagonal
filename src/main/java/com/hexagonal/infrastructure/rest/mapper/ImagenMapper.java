package com.hexagonal.infrastructure.rest.mapper;

import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.infrastructure.rest.dto.ImagenMongoEntityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImagenMapper {
    ImagenMongoEntityDto toDto(ImagenMongo imagenMongo);
    ImagenMongo toDomain(ImagenMongoEntityDto imagenMongoEntityDto);
    List<ImagenMongoEntityDto> toAllDto(List<ImagenMongo> mongoList);
}

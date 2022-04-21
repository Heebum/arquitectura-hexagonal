package com.hexagonal.infrastructure.db.entity;

import com.hexagonal.domain.Persona;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ImagenMongoEntity")
public class ImagenMongoEntity {
    @Id
    private String _id;
    private String foto;
    private Persona persona;

}

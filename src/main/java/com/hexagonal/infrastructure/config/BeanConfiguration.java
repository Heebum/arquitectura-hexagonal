package com.hexagonal.infrastructure.config;

import com.hexagonal.domain.repository.ImagenRepository;
import com.hexagonal.domain.repository.PersonaRepository;
import com.hexagonal.domain.service.ImagenService;
import com.hexagonal.domain.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonaService personaService(PersonaRepository personaRepository){
        return new PersonaService(personaRepository);
    }
    @Bean
    public ImagenService imagenService(PersonaRepository personaRepository,ImagenRepository imagenRepository){
        return new ImagenService(personaRepository,imagenRepository);
    }
//    @Bean
//    public DataImagenRepositoryImpl dataImagenRepositoryImpl(DataImagenRepository dataImagenRepository){
//        return new DataImagenRepositoryImpl(dataImagenRepository);
//    }
}

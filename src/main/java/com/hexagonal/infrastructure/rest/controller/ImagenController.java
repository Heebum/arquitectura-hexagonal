package com.hexagonal.infrastructure.rest.controller;

import com.hexagonal.domain.ImagenMongo;
import com.hexagonal.domain.service.ImagenService;
import com.hexagonal.infrastructure.rest.mapper.ImagenMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/imagenMongo")
@RestController
public class ImagenController {

    @Autowired
    ImagenService imagenService;
    @Autowired
    ImagenMapper imagenMapper;

    @ApiOperation(value="Crear nueva Imagen", notes="Proporciona una operación para crear un nuevo objeto Imagen y devolver su identificador")
    @ApiResponses(value= {
            @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
    })
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> createImage(@RequestParam("file") MultipartFile imageFile, @RequestParam String fk_persona){
        try {
            return new ResponseEntity<>(imagenMapper.toDto(imagenService.save(imageFile,fk_persona)), HttpStatus.CREATED);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="Obtener imagen por id", notes="Proporciona una operación para obtener un objeto Imagen por su identificador")
    @ApiResponses(value={
            @ApiResponse(code=200, message="OK", response= ImagenMongo.class),
            @ApiResponse(code=404, message="Not Found", response=String.class),
            @ApiResponse(code=500, message="Internal Server Error", response=String.class)
    })
    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> getImagenById(@PathVariable String id){
        try {
            return new ResponseEntity<>(imagenMapper.toDto(imagenService.findImagenById(id)), HttpStatus.OK);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/allimages")
    public ResponseEntity<Object> findAllImages() {
        try {
            return new ResponseEntity<>(imagenMapper.toAllDto(imagenService.findImagenAll()),HttpStatus.OK);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping(value = "/{id}",produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> updateImage(@RequestParam String _id, @RequestParam("file") MultipartFile imageFile, @RequestParam String fk_persona){
        try {
            return new ResponseEntity<>(imagenMapper.toDto(imagenService.update(imageFile,fk_persona,_id)), HttpStatus.CREATED);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}",produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> deleteImage(@PathVariable String _id){
        try {
            return new ResponseEntity<>(imagenMapper.toDto(imagenService.delete(_id)),HttpStatus.OK);
        }catch (Exception e){
            Map<String,String> errors = new HashMap<>();
            errors.put("mensaje",e.getMessage());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
    }
}

package com.example.ms_categorias.mapper;

import com.example.ms_categorias.dto.CategoriaRequest;
import com.example.ms_categorias.dto.CategoriaResponse;
import com.example.ms_categorias.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component

public class CategoriaMapper {

    public CategoriaEntity toEntity(CategoriaRequest request) {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setNombre(request.getNombre());
        return entity;
    }

    public CategoriaResponse toResponse(CategoriaEntity entity) {
        return new CategoriaResponse(
                entity.getId(),
                entity.getNombre()
        );
    }

    public void updateEntity(CategoriaEntity entity, CategoriaRequest request) {
        entity.setNombre(request.getNombre());
    }

}

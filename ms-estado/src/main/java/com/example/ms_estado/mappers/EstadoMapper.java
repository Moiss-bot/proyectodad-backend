package com.example.ms_estado.mappers;

import com.example.ms_estado.dto.EstadoRequest;
import com.example.ms_estado.dto.EstadoResponse;
import com.example.ms_estado.entity.EstadoEntity;
import org.springframework.stereotype.Component;

@Component

public class EstadoMapper {

    // Convierte lo que recibimos del cliente (Request) a lo que entiende la BD (Entity)
    public EstadoEntity toEntity(EstadoRequest request) {
        EstadoEntity entity = new EstadoEntity();
        entity.setNombre(request.getNombre());
        return entity;
    }

    // Convierte lo que sale de la BD (Entity) a lo que devolvemos al cliente (Response)
    public EstadoResponse toResponse(EstadoEntity entity) {
        return new EstadoResponse(
                entity.getId(),
                entity.getNombre()
        );
    }

    // Para cuando quieres actualizar un registro existente
    public void updateEntity(EstadoEntity entity, EstadoRequest request) {
        entity.setNombre(request.getNombre());
    }
}

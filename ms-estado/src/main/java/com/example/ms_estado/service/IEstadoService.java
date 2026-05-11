package com.example.ms_estado.service;

import com.example.ms_estado.dto.EstadoRequest;
import com.example.ms_estado.dto.EstadoResponse;

import java.util.List;

public interface IEstadoService {
    // Crear un nuevo estado
    EstadoResponse crear(EstadoRequest request) throws Exception;

    // Listar todos los estados
    List<EstadoResponse> listar();

    // Buscar por ID
    EstadoResponse buscarPorId(Long id);

    // Actualizar un estado existente
    EstadoResponse actualizar(Long id, EstadoRequest request);

    // Eliminar físicamente el registro
    void eliminar(Long id);

    // Búsqueda personalizada por nombre
    List<EstadoResponse> buscarPorNombre(String nombre);
}

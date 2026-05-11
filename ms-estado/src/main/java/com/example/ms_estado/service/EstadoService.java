package com.example.ms_estado.service;

import com.example.ms_estado.dto.EstadoRequest;
import com.example.ms_estado.dto.EstadoResponse;
import com.example.ms_estado.entity.EstadoEntity;
import com.example.ms_estado.errors.EstadoNotFoundException;
import com.example.ms_estado.manager.IEstadoManager;
import com.example.ms_estado.mappers.EstadoMapper;
import com.example.ms_estado.repository.EstadoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class EstadoService implements  IEstadoService {

    private final EstadoRepository repository;
    private final EstadoMapper mapper;
    private final IEstadoManager estadoManager; // <--- Agregado

    @Autowired
    public EstadoService(EstadoRepository repository, EstadoMapper mapper, IEstadoManager estadoManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.estadoManager = estadoManager;
    }

    @Override
    public List<EstadoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EstadoResponse buscarPorId(Long id) {
        EstadoEntity entity = repository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    @CircuitBreaker(name = "estadoCB", fallbackMethod = "fallbackMethod")
    public EstadoResponse crear(EstadoRequest request) throws Exception {
        // Validación local
        repository.findByNombre(request.getNombre()).ifPresent(e -> {
            throw new IllegalArgumentException("Ya existe un estado con el nombre: " + request.getNombre());
        });

        // Llamada al Manager (Simulando validación externa)
        //String rptaExterno = estadoManager.validarNombreEstadoExterno(request.getNombre());

        EstadoEntity entity = mapper.toEntity(request);
        // Podrías guardar algo de la respuesta externa si quieres
        return mapper.toResponse(repository.save(entity));
    }

    // Método Fallback para el Circuit Breaker
    public EstadoResponse fallbackMethod(EstadoRequest request, Exception e) {
        // En caso de fallo, devolvemos un objeto temporal con un mensaje de error
        EstadoResponse response = new EstadoResponse();
        response.setId(0L);
        response.setNombre("SERVICIO TEMPORALMENTE NO DISPONIBLE - FALLBACK");
        return response;
    }

    @Override
    public EstadoResponse actualizar(Long id, EstadoRequest request) {
        EstadoEntity entity = repository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));

        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        EstadoEntity entity = repository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));

        // En estados solemos hacer eliminación física,
        // ya que la entidad no tiene un campo propio de "estado"
        repository.delete(entity);
    }

    @Override
    public List<EstadoResponse> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

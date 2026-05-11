package com.example.ms_categorias.service;

import com.example.ms_categorias.dto.CategoriaRequest;
import com.example.ms_categorias.dto.CategoriaResponse;
import com.example.ms_categorias.entity.CategoriaEntity;
import com.example.ms_categorias.errors.CategoriaNotFoundException;
import com.example.ms_categorias.manager.ICategoriaManager;
import com.example.ms_categorias.mapper.CategoriaMapper;
import com.example.ms_categorias.repository.CategoriaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final ICategoriaManager categoriaManager;

    @Autowired
    public CategoriaService(CategoriaRepository repository, CategoriaMapper mapper, ICategoriaManager categoriaManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoriaManager = categoriaManager;
    }

    @Override
    public List<CategoriaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponse buscarPorId(Long id) {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    @CircuitBreaker(name = "categoriaCB", fallbackMethod = "fallbackMethod")
    public CategoriaResponse crear(CategoriaRequest request) throws Exception {
        repository.findByNombre(request.getNombre()).ifPresent(c -> {
            throw new IllegalArgumentException("Ya existe una categoria con el nombre: " + request.getNombre());
        });

        // Llamada al Manager (Simulando validación externa)
        // String rptaExterno = categoriaManager.validarCategoriaExterna(request.getNombre());

        CategoriaEntity entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public CategoriaResponse fallbackMethod(CategoriaRequest request, Exception e) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(0L);
        response.setNombre("SERVICIO TEMPORALMENTE NO DISPONIBLE - FALLBACK");
        return response;
    }

    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        repository.delete(entity);
    }

    @Override
    public List<CategoriaResponse> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

}

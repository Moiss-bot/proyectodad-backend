package pe.edu.upeu.msmelamine.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.msmelamine.client.EstadoClient;
import pe.edu.upeu.msmelamine.dto.EstadoResponse;
import pe.edu.upeu.msmelamine.dto.MelamineRequest;
import pe.edu.upeu.msmelamine.dto.MelamineResponse;
import pe.edu.upeu.msmelamine.entity.MelamineEntity;
import pe.edu.upeu.msmelamine.errors.MelamineNotFoundException;
import pe.edu.upeu.msmelamine.manager.IMelamineManager;
import pe.edu.upeu.msmelamine.mapper.MelamineMapper;
import pe.edu.upeu.msmelamine.repository.MelamineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class MelamineService implements IMelamineService {

    private final MelamineRepository repository;
    private final MelamineMapper mapper;
    private final IMelamineManager manager;
    private final EstadoClient estadoClient;

    @Autowired
    public MelamineService(MelamineRepository repository, MelamineMapper mapper,
                           IMelamineManager manager, EstadoClient estadoClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.manager = manager;
        this.estadoClient = estadoClient;
    }

    @Override
    @CircuitBreaker(name = "melamineCB", fallbackMethod = "fallbackMethod")
    public MelamineResponse crear(MelamineRequest request) throws Exception {
        // 1. Validar estado en el otro microservicio
        manager.validarEstadoExterno(request.getEstadoId());

        // 2. Guardar en PostgreSQL
        MelamineEntity entity = mapper.toEntity(request);
        MelamineResponse response = mapper.toResponse(repository.save(entity));

        // 3. Enriquecer datos (traer el nombre del estado)
        EstadoResponse estado = estadoClient.buscarPorId(request.getEstadoId());
        response.setEstadoNombre(estado.getNombre());

        return response;
    }

    @Override
    public List<MelamineResponse> listar() {
        return repository.findAll().stream().map(entity -> {
            MelamineResponse res = mapper.toResponse(entity);
            try {
                EstadoResponse estado = estadoClient.buscarPorId(entity.getEstadoId());
                res.setEstadoNombre(estado.getNombre());
            } catch (Exception e) {
                res.setEstadoNombre("Estado no disponible");
            }
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public MelamineResponse buscarPorId(Long id) {
        MelamineEntity entity = repository.findById(id)
                .orElseThrow(() -> new MelamineNotFoundException(id));
        MelamineResponse res = mapper.toResponse(entity);
        try {
            EstadoResponse estado = estadoClient.buscarPorId(entity.getEstadoId());
            res.setEstadoNombre(estado.getNombre());
        } catch (Exception e) {
            res.setEstadoNombre("Estado no disponible");
        }
        return res;
    }

    // Método Fallback para el Circuit Breaker
    public MelamineResponse fallbackMethod(MelamineRequest request, Exception e) {
        System.err.println("Causa del Fallback en Melamine: " + e.getMessage());
        MelamineResponse response = new MelamineResponse();
        response.setId(0L);
        response.setMarca("SERVICIO DE ESTADOS CAÍDO - FALLBACK");
        return response;
    }

    @Override
    public MelamineResponse actualizar(Long id, MelamineRequest request) {
        MelamineEntity entity = repository.findById(id)
                .orElseThrow(() -> new MelamineNotFoundException(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        MelamineEntity entity = repository.findById(id)
                .orElseThrow(() -> new MelamineNotFoundException(id));
        repository.delete(entity);
    }

}

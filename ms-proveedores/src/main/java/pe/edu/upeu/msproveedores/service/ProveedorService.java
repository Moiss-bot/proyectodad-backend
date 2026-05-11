package pe.edu.upeu.msproveedores.service;

import pe.edu.upeu.msproveedores.dto.ProveedorRequest;
import pe.edu.upeu.msproveedores.dto.ProveedorResponse;
import pe.edu.upeu.msproveedores.entity.ProveedorEntity;
import pe.edu.upeu.msproveedores.errors.ProveedorNotFoundException;
import pe.edu.upeu.msproveedores.manager.IProveedorManager;
import pe.edu.upeu.msproveedores.mapper.ProveedorMapper;
import pe.edu.upeu.msproveedores.repository.ProveedorRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProveedorService implements IProveedorService {

    private final ProveedorRepository repository;
    private final ProveedorMapper mapper;
    private final IProveedorManager manager;

    @Autowired
    public ProveedorService(ProveedorRepository repository, ProveedorMapper mapper, IProveedorManager manager) {
        this.repository = repository;
        this.mapper = mapper;
        this.manager = manager;
    }

    @Override
    public List<ProveedorResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponse buscarPorId(Long id) {
        ProveedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    @CircuitBreaker(name = "proveedoresCB", fallbackMethod = "fallbackMethod")
    public ProveedorResponse crear(ProveedorRequest request) throws Exception {
        // Validación de duplicados por nombre y apellido
        repository.findByNombresAndApellidos(request.getNombres(), request.getApellidos()).ifPresent(p -> {
            throw new IllegalArgumentException("Ya existe un proveedor con ese nombre: " + request.getNombres());
        });

        // Validación externa vía Manager (Llamada a ms-categoria)
        manager.validarCategoriaExterno(request.getCategoriaId());

        ProveedorEntity entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    // Método Fallback para el Circuit Breaker
    public ProveedorResponse fallbackMethod(ProveedorRequest request, Exception e) {
        ProveedorResponse response = new ProveedorResponse();
        response.setId(0L);
        response.setNombres("SERVICIO TEMPORALMENTE NO DISPONIBLE - FALLBACK");
        return response;
    }

    @Override
    public ProveedorResponse actualizar(Long id, ProveedorRequest request) {
        ProveedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));

        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        ProveedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));
        repository.delete(entity);
    }

    @Override
    public List<ProveedorResponse> buscarPorNombre(String nombres) {
        return repository.findByNombresContainingIgnoreCase(nombres)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

}

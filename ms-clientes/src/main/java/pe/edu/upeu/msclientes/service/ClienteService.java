package pe.edu.upeu.msclientes.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.msclientes.dto.ClienteRequest;
import pe.edu.upeu.msclientes.dto.ClienteResponse;
import pe.edu.upeu.msclientes.entity.ClienteEntity;
import pe.edu.upeu.msclientes.errors.ClienteNotFoundException;
import pe.edu.upeu.msclientes.manager.IClienteManager;
import pe.edu.upeu.msclientes.mappers.ClienteMapper;
import pe.edu.upeu.msclientes.repository.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements  IClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final IClienteManager clienteManager;

    @Autowired
    public ClienteService(ClienteRepository repository, ClienteMapper mapper, IClienteManager clienteManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.clienteManager = clienteManager;
    }

    @Override
    public List<ClienteResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    public ClienteResponse buscarPorDni(String dni) {
        ClienteEntity entity = repository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("No existe el cliente con DNI: " + dni));
        return mapper.toResponse(entity);
    }

    @Override
    @CircuitBreaker(name = "clientesCB", fallbackMethod = "fallbackMethod")
    public ClienteResponse crear(ClienteRequest request) throws Exception{
        // Validación local (DB)
        repository.findByDni(request.getDni()).ifPresent(c -> {
            throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + request.getDni());
        });

        // 3. LLAMADA AL MANAGER
        String rptaExterno = clienteManager.validarDniExterno(request.getDni());

        ClienteEntity entity = mapper.toEntity(request);
        entity.setEstado("ACTIVO - " + rptaExterno);
        return mapper.toResponse(repository.save(entity));
    }

    public ClienteResponse fallbackMethod(ClienteRequest request, Exception e) {
        ClienteEntity entity = mapper.toEntity(request);
        entity.setEstado("FALLBACK - SERVICIO DE VALIDACIÓN NO DISPONIBLE");
        return mapper.toResponse(entity);
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        entity.setEstado("INACTIVO");
        repository.save(entity);
    }

    @Override
    public List<ClienteResponse> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> buscarPorApellido(String apellido) {
        return repository.findByApellidoContainingIgnoreCase(apellido)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

}
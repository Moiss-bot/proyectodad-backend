package pe.edu.upeu.msaccesorios.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.msaccesorios.dto.AccesorioRequest;
import pe.edu.upeu.msaccesorios.dto.AccesorioResponse;
import pe.edu.upeu.msaccesorios.entity.AccesorioEntity;
import pe.edu.upeu.msaccesorios.errors.AccesorioNotFoundException;
import pe.edu.upeu.msaccesorios.errors.ValidationException;
import pe.edu.upeu.msaccesorios.manager.IAccesorioManager;
import pe.edu.upeu.msaccesorios.mappers.AccesorioMapper;
import pe.edu.upeu.msaccesorios.repository.AccesorioRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccesorioService implements IAccesorioService {

    private final AccesorioRepository repository;
    private final AccesorioMapper mapper;
    private final IAccesorioManager accesorioManager;

    @Autowired
    public AccesorioService(AccesorioRepository repository, AccesorioMapper mapper, IAccesorioManager accesorioManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.accesorioManager = accesorioManager;
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackListar")
    public List<AccesorioResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackBuscarPorId")
    public AccesorioResponse buscarPorId(Long id) {
        AccesorioEntity entity = repository.findById(id)
                .orElseThrow(() -> new AccesorioNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackCrear")
    public AccesorioResponse crear(AccesorioRequest request) throws Exception {
        // 🎯 ¡CAMBIA LA LÍNEA DE ABAJO PARA USAR EL NUEVO MÉTODO SIMÉTRICO CON CLIENTES!
        repository.findByNombreContainingIgnoreCase(request.getNombre()).ifPresent(a -> {
            throw new IllegalArgumentException("Ya existe un accesorio con el nombre: " + request.getNombre());
        });

        AccesorioEntity entity = mapper.toEntity(request);
        entity.setEstado("ACTIVO");
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackActualizar")
    public AccesorioResponse actualizar(Long id, AccesorioRequest request) {
        AccesorioEntity entity = repository.findById(id)
                .orElseThrow(() -> new AccesorioNotFoundException(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackEliminar")
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. No existe el accesorio con ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackBuscarPorNombre")
    public List<AccesorioResponse> buscarPorNombre(String nombre) {
        // Delegado al repositorio emulando 'findByNombreContainingIgnoreCase' de Clientes
        List<AccesorioResponse> resultado = repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron accesorios con el nombre: " + nombre);
        }
        return resultado;
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackBuscarPorCategoria")
    public List<AccesorioResponse> buscarPorCategoria(String categoria) {
        List<AccesorioResponse> resultado = repository.findByCategoriaContainingIgnoreCase(categoria)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron accesorios con la categoría: " + categoria);
        }
        return resultado;
    }

    @Override
    @CircuitBreaker(name = "accesorioService", fallbackMethod = "fallbackListarConStock")
    public List<AccesorioResponse> listarConStock() {
        return repository.findByStockGreaterThan(0)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 MÉTODOS FALLBACK (Mantienen resiliencia operativa)

    public List<AccesorioResponse> fallbackListar(Throwable t) {
        return Collections.emptyList();
    }

    public AccesorioResponse fallbackBuscarPorId(Long id, Throwable t) {
        if (t instanceof AccesorioNotFoundException) throw (AccesorioNotFoundException) t;
        AccesorioResponse response = new AccesorioResponse();
        response.setNombre("Servicio no disponible");
        response.setEstado("NO DISPONIBLE");
        return response;
    }

    public AccesorioResponse fallbackCrear(AccesorioRequest request, Throwable t) {
        if (t instanceof ValidationException) throw (ValidationException) t;
        if (t instanceof IllegalArgumentException) throw (IllegalArgumentException) t;
        AccesorioResponse response = new AccesorioResponse();
        response.setNombre("Servicio no disponible temporalmente");
        response.setEstado("NO DISPONIBLE");
        return response;
    }

    public AccesorioResponse fallbackActualizar(Long id, AccesorioRequest request, Throwable t) {
        if (t instanceof ValidationException) throw (ValidationException) t;
        if (t instanceof IllegalArgumentException) throw (IllegalArgumentException) t;
        if (t instanceof AccesorioNotFoundException) throw (AccesorioNotFoundException) t;
        AccesorioResponse response = new AccesorioResponse();
        response.setNombre("No se pudo actualizar, servicio no disponible");
        response.setEstado("NO DISPONIBLE");
        return response;
    }

    public void fallbackEliminar(Long id, Throwable t) {
        if (t instanceof AccesorioNotFoundException) throw (AccesorioNotFoundException) t;
    }

    public List<AccesorioResponse> fallbackBuscarPorNombre(String nombre, Throwable t) {
        if (t instanceof IllegalArgumentException) throw (IllegalArgumentException) t;
        return Collections.emptyList();
    }

    public List<AccesorioResponse> fallbackBuscarPorCategoria(String categoria, Throwable t) {
        if (t instanceof IllegalArgumentException) throw (IllegalArgumentException) t;
        return Collections.emptyList();
    }

    public List<AccesorioResponse> fallbackListarConStock(Throwable t) {
        return Collections.emptyList();
    }
}
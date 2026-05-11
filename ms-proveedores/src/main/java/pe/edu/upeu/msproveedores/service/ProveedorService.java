package pe.edu.upeu.msproveedores.service;

import pe.edu.upeu.msproveedores.clients.CategoriaClient;
import pe.edu.upeu.msproveedores.dto.CategoriaResponse;
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
    private final CategoriaClient categoriaClient;

    @Autowired // 👈 2. La inyectamos en el constructor
    public ProveedorService(ProveedorRepository repository, ProveedorMapper mapper,
                            IProveedorManager manager, CategoriaClient categoriaClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.manager = manager;
        this.categoriaClient = categoriaClient;
    }

    @Override
    public List<ProveedorResponse> listar() {
        return repository.findAll().stream().map(entity -> {
            ProveedorResponse res = mapper.toResponse(entity);
            // 🚀 Llenamos el nombre de la categoría para cada elemento de la lista
            try {
                CategoriaResponse cat = categoriaClient.buscarPorId(entity.getCategoriaId());
                res.setCategoriaNombre(cat.getNombre());
            } catch (Exception e) {
                res.setCategoriaNombre("Nombre no disponible");
            }
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public ProveedorResponse buscarPorId(Long id) {
        ProveedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));

        ProveedorResponse res = mapper.toResponse(entity);
        // 🚀 Llenamos el nombre para la respuesta individual
        try {
            CategoriaResponse cat = categoriaClient.buscarPorId(entity.getCategoriaId());
            res.setCategoriaNombre(cat.getNombre());
        } catch (Exception e) {
            res.setCategoriaNombre("Nombre no disponible");
        }
        return res;
    }

    @Override
    @CircuitBreaker(name = "proveedoresCB", fallbackMethod = "fallbackMethod")
    public ProveedorResponse crear(ProveedorRequest request) throws Exception {
        // Validación de duplicados
        repository.findByNombresAndApellidos(request.getNombres(), request.getApellidos()).ifPresent(p -> {
            throw new IllegalArgumentException("Ya existe un proveedor con ese nombre: " + request.getNombres());
        });

        // Validación externa vía Manager (Tu lógica se mantiene intacta)
        manager.validarCategoriaExterno(request.getCategoriaId());

        // 3. 🚀 PROCESO DE GUARDADO Y LLENADO DE NOMBRE
        ProveedorEntity entity = mapper.toEntity(request);
        ProveedorResponse response = mapper.toResponse(repository.save(entity));

        // Traemos el nombre desde el microservicio de categorías usando Feign
        CategoriaResponse cat = categoriaClient.buscarPorId(request.getCategoriaId());
        response.setCategoriaNombre(cat.getNombre()); // 👈 Aquí le quitamos el 'null'

        return response;
    }

    // Método Fallback para el Circuit Breaker
    public ProveedorResponse fallbackMethod(ProveedorRequest request, Exception e) {
        // Imprime esto para que veas el error real en la consola de IntelliJ
        System.err.println("Causa del Fallback: " + e.getMessage());
        e.printStackTrace();

        ProveedorResponse response = new ProveedorResponse();
        response.setId(0L);
        response.setNombres("FALLBACK: " + e.getMessage()); // Agrega el mensaje al JSON
        return response;
    }

    @Override
    public ProveedorResponse actualizar(Long id, ProveedorRequest request) {
        ProveedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));

        mapper.updateEntity(entity, request);
        ProveedorResponse res = mapper.toResponse(repository.save(entity));

        // 🚀 También podemos llenar el nombre en el actualizar
        try {
            CategoriaResponse cat = categoriaClient.buscarPorId(request.getCategoriaId());
            res.setCategoriaNombre(cat.getNombre());
        } catch (Exception e) {
            res.setCategoriaNombre("Nombre no disponible");
        }
        return res;
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
                .map(entity -> {
                    ProveedorResponse res = mapper.toResponse(entity);
                    try {
                        CategoriaResponse cat = categoriaClient.buscarPorId(entity.getCategoriaId());
                        res.setCategoriaNombre(cat.getNombre());
                    } catch (Exception e) {
                        res.setCategoriaNombre("No disponible");
                    }
                    return res;
                })
                .collect(Collectors.toList());
    }
}

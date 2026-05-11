package pe.edu.upeu.msproveedores.controller;

import pe.edu.upeu.msproveedores.dto.ProveedorRequest;
import pe.edu.upeu.msproveedores.dto.ProveedorResponse;
import pe.edu.upeu.msproveedores.service.IProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")


public class ProveedorController {

    private final IProveedorService service;

    public ProveedorController(IProveedorService service) {
        this.service = service;
    }

    // 1. Listar todos
    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 2. Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 3. Buscar por Nombre (El que faltaba)
    @GetMapping("/buscar")
    public ResponseEntity<List<ProveedorResponse>> buscarPorNombre(@RequestParam String nombres) {
        return ResponseEntity.ok(service.buscarPorNombre(nombres));
    }

    // 4. Crear nuevo proveedor
    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(@Valid @RequestBody ProveedorRequest request) throws Exception {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    // 5. Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    // 6. Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

package pe.edu.upeu.msmelamine.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msmelamine.dto.MelamineRequest;
import pe.edu.upeu.msmelamine.dto.MelamineResponse;
import pe.edu.upeu.msmelamine.service.IMelamineService;

import java.util.List;

@RestController
@RequestMapping("/api/melamine")

public class MelamineController {

    private final IMelamineService service;

    public MelamineController(IMelamineService service) {
        this.service = service;
    }

    // 1. Listar todas las melaminas
    @GetMapping
    public ResponseEntity<List<MelamineResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 2. Buscar una melamina por ID
    @GetMapping("/{id}")
    public ResponseEntity<MelamineResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 3. Crear nueva melamina (Usa el Circuit Breaker del Service)
    @PostMapping
    public ResponseEntity<MelamineResponse> crear(@Valid @RequestBody MelamineRequest request) throws Exception {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    // 4. Actualizar melamina existente
    @PutMapping("/{id}")
    public ResponseEntity<MelamineResponse> actualizar(@PathVariable Long id, @Valid @RequestBody MelamineRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    // 5. Eliminar melamina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

package pe.edu.upeu.msproyectos.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msproyectos.dtos.ProyectoDetalleResponse;
import pe.edu.upeu.msproyectos.dtos.ProyectoRequest;
import pe.edu.upeu.msproyectos.dtos.ProyectoResponse;
import pe.edu.upeu.msproyectos.services.ProyectoService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<List<ProyectoDetalleResponse>> listAll() {
        return ResponseEntity.ok(proyectoService.listarDetalle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDetalleResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.buscarDetallePorId(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest request) {
        ProyectoResponse creado = proyectoService.crear(request);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ProyectoRequest request) {
        ProyectoResponse actualizado = proyectoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProyectoResponse>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(proyectoService.buscarPorNombre(nombre));
    }

    @GetMapping("/detalle")
    public ResponseEntity<List<ProyectoDetalleResponse>> listarDetalle() {
        return ResponseEntity.ok(proyectoService.listarDetalle());
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<ProyectoDetalleResponse> buscarDetallePorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.buscarDetallePorId(id));
    }
}
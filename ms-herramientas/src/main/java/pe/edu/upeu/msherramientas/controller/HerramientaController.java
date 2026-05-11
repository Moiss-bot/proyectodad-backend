package pe.edu.upeu.msherramientas.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msherramientas.dto.HerramientaResponse;
import pe.edu.upeu.msherramientas.service.HerramientaService;

import java.util.List;

@RestController
@RequestMapping("/api/herramientas")
public class HerramientaController {
    private final HerramientaService herramientaService;

    public HerramientaController(HerramientaService herrmientaService) { this.herramientaService = herrmientaService; }

    @GetMapping
    public ResponseEntity<List<HerramientaResponse>> listAll(){
        List<HerramientaResponse> herramientas = herramientaService.listar();
        return ResponseEntity.ok(herramientas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HerramientaResponse> buscarPorId(@PathVariable Long id){
        HerramientaResponse herramienta = herramientaService.buscarPorId(id);
        return ResponseEntity.ok(herramienta);
    }

    @PostMapping
    public ResponseEntity<HerramientaResponse> crear(@Valid @RequestBody HerramientaResponse herramientaResponse){
        HerramientaResponse creado = herramientaService.crear(herramientaResponse);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HerramientaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody HerramientaResponse herramientaResponse){
        HerramientaResponse actualizado = herramientaService.actualizar(id, herramientaResponse);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<HerramientaResponse>> buscarPorNombre(@RequestParam String nombre){
       return ResponseEntity.ok(herramientaService.buscarPorNombre(nombre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        herramientaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/resiliencia-test")
    public ResponseEntity<String> probarResiliencia(@PathVariable Long id) {
        String respuesta = herramientaService.obtenerInformacionAdicional(id);
        return ResponseEntity.ok(respuesta);
    }

}

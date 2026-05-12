package pe.edu.upeu.msmelamine.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upeu.msmelamine.dto.EstadoResponse;

@FeignClient(name = "ms-estado")

public interface EstadoClient {
    @GetMapping("/api/estados/{id}")
    EstadoResponse buscarPorId(@PathVariable("id") Long id);
}

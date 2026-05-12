package pe.edu.upeu.msproveedores.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upeu.msproveedores.dto.CategoriaResponse;

@FeignClient(name = "ms-categorias")
public interface CategoriaClient {
    @GetMapping("/api/categorias/{id}")
    CategoriaResponse buscarPorId(@PathVariable("id") Long id);
}

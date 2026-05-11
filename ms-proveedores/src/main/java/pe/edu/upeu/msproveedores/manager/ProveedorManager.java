package pe.edu.upeu.msproveedores.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class ProveedorManager implements IProveedorManager{

    private final RestTemplate restTemplate;

    @Autowired
    public ProveedorManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String validarCategoriaExterno(long categoriaId) throws Exception {
        try {
            restTemplate.getForEntity("http://localhost:9090/api/validar/" + categoriaId, String.class);
            return "OK";
        } catch (Exception ex) {
            throw ex;
        }
    }

}

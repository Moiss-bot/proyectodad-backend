package pe.edu.upeu.msclientes.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteManager implements IClienteManager {

    private final RestTemplate restTemplate;

    @Autowired
    public ClienteManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String validarDniExterno(String dni) throws Exception {
        try {
            // Simulamos la llamada al puerto 9090 del ejemplo de tu clase
            restTemplate.getForEntity("http://localhost:9090/api/validar/" + dni, String.class);
            return "DNI Validado Correctamente";
        } catch (Exception ex) {
            // Re-lanzamos la excepción para que el Circuit Breaker la atrape
            throw ex;
        }
    }
}

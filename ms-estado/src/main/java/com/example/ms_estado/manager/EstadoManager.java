package com.example.ms_estado.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class EstadoManager implements IEstadoManager {

    private final RestTemplate restTemplate;

    @Autowired
    public EstadoManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String validarNombreEstadoExterno(String nombre) throws Exception {
        try {
            // Siguiendo la lógica del profe: intentamos llamar al validador externo
            // Si el puerto 9090 no responde, saltará al catch
            restTemplate.getForEntity("http://localhost:9090/api/validar/" + nombre, String.class);
            return "Estado validado correctamente";
        } catch (Exception ex) {
            // El profe relanza la excepción: esto activará el Circuit Breaker
            throw ex;
        }
    }

}

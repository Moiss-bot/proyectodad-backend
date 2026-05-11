package com.example.ms_categorias.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class CategoriaManager implements ICategoriaManager{

    private final RestTemplate restTemplate;

    @Autowired
    public CategoriaManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String validarCategoriaExterna(String nombre) throws Exception {
        try {
            restTemplate.getForEntity("http://localhost:9090/api/validar/" + nombre, String.class);
            return "Categoria validada correctamente";
        } catch (Exception ex) {
            throw ex;
        }
    }

}

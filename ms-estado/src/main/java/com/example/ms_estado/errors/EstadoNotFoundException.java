package com.example.ms_estado.errors;

public class EstadoNotFoundException extends RuntimeException {
    public EstadoNotFoundException(Long id) {
        super("El Estado con el id " + id + " no existe");
    }
}

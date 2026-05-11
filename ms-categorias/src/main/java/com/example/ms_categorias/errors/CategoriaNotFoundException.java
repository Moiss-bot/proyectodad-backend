package com.example.ms_categorias.errors;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("La categoría con el id " + id + " no existe");
    }
}

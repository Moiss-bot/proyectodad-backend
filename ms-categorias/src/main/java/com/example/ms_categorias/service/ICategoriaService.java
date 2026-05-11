package com.example.ms_categorias.service;

import com.example.ms_categorias.dto.CategoriaRequest;
import com.example.ms_categorias.dto.CategoriaResponse;
import java.util.List;

public interface ICategoriaService {

    CategoriaResponse crear(CategoriaRequest request) throws Exception;
    List<CategoriaResponse> listar();
    CategoriaResponse buscarPorId(Long id);
    CategoriaResponse actualizar(Long id, CategoriaRequest request);
    void eliminar(Long id);
    List<CategoriaResponse> buscarPorNombre(String nombre);

}

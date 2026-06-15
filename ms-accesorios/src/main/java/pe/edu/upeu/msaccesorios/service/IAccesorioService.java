package pe.edu.upeu.msaccesorios.service;

import pe.edu.upeu.msaccesorios.dto.AccesorioRequest;
import pe.edu.upeu.msaccesorios.dto.AccesorioResponse;

import java.util.List;

public interface IAccesorioService {
    List<AccesorioResponse> listar();
    AccesorioResponse buscarPorId(Long id);
    AccesorioResponse crear(AccesorioRequest request) throws Exception;
    AccesorioResponse actualizar(Long id, AccesorioRequest request);
    void eliminar(Long id);
    List<AccesorioResponse> buscarPorNombre(String nombre);
    List<AccesorioResponse> buscarPorCategoria(String categoria);
    List<AccesorioResponse> listarConStock();
}
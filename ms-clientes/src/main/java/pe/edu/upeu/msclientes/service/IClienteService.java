package pe.edu.upeu.msclientes.service;

import pe.edu.upeu.msclientes.dto.ClienteRequest;
import pe.edu.upeu.msclientes.dto.ClienteResponse;

import java.util.List;

public interface IClienteService {
    ClienteResponse crear(ClienteRequest request) throws Exception;

    List<ClienteResponse> listar();
    ClienteResponse buscarPorId(Long id);
    ClienteResponse buscarPorDni(String dni);
    ClienteResponse actualizar(Long id, ClienteRequest request);
    void eliminar(Long id);
    List<ClienteResponse> buscarPorNombre(String nombre);
    List<ClienteResponse> buscarPorApellido(String apellido);

}

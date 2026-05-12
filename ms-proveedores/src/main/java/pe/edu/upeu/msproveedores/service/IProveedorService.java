package pe.edu.upeu.msproveedores.service;

import pe.edu.upeu.msproveedores.dto.ProveedorRequest;
import pe.edu.upeu.msproveedores.dto.ProveedorResponse;
import java.util.List;

public interface IProveedorService {

    ProveedorResponse crear(ProveedorRequest request) throws Exception;
    List<ProveedorResponse> listar();
    ProveedorResponse buscarPorId(Long id);
    ProveedorResponse actualizar(Long id, ProveedorRequest request);
    void eliminar(Long id);
    List<ProveedorResponse> buscarPorNombre(String nombres);

}

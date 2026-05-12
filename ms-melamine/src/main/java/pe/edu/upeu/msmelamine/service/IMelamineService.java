package pe.edu.upeu.msmelamine.service;

import pe.edu.upeu.msmelamine.dto.MelamineRequest;
import pe.edu.upeu.msmelamine.dto.MelamineResponse;
import java.util.List;

public interface IMelamineService {
    MelamineResponse crear(MelamineRequest request) throws Exception;
    List<MelamineResponse> listar();
    MelamineResponse buscarPorId(Long id);
    MelamineResponse actualizar(Long id, MelamineRequest request);
    void eliminar(Long id);
}

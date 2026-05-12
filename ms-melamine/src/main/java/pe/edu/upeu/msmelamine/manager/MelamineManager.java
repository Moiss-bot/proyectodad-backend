package pe.edu.upeu.msmelamine.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.msmelamine.client.EstadoClient;

@Component

public class MelamineManager implements IMelamineManager{
    private final EstadoClient estadoClient;

    @Autowired
    public MelamineManager(EstadoClient estadoClient) {
        this.estadoClient = estadoClient;
    }

    @Override
    public String validarEstadoExterno(Long estadoId) throws Exception {
        try {
            // Llamamos a ms-estado vía Feign
            estadoClient.buscarPorId(estadoId);
            return "OK";
        } catch (Exception ex) {
            throw new Exception("Estado no válido o no encontrado: " + ex.getMessage());
        }
    }

}

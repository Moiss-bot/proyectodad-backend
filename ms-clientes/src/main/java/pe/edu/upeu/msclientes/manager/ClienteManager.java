package pe.edu.upeu.msclientes.manager;

import org.springframework.stereotype.Component;


@Component
public class ClienteManager implements IClienteManager {

    @Override
    public String validarDniExterno(String dni) throws Exception {
        // Validación externa desactivada
        return "DNI Validado Correctamente";
    }
}
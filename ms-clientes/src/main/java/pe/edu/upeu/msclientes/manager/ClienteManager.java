package pe.edu.upeu.msclientes.manager;

import org.springframework.stereotype.Component;


@Component
public class ClienteManager implements IClienteManager {

    @Override
    public String validarTelefonoExterno(String telefono) throws Exception {
        // Validación externa desactivada
        return "Teléfono Validado Correctamente";
    }
}
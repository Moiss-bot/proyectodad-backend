package pe.edu.upeu.msaccesorios.manager;

import org.springframework.stereotype.Component;

@Component
public class AccesorioManager implements IAccesorioManager {

    @Override
    public String validarStockExterno(Integer stock) throws Exception {
        // Validación externa desactivada (Estructura idéntica a Clientes)
        return "Stock Validado Correctamente";
    }
}
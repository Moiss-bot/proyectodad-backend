package pe.edu.upeu.msproveedores.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pe.edu.upeu.msproveedores.clients.CategoriaClient;

@Component

public class ProveedorManager implements IProveedorManager{

    private final CategoriaClient categoriaClient; // 👈 Cambiamos RestTemplate por tu Feign Client

    @Autowired
    public ProveedorManager(CategoriaClient categoriaClient) {
        this.categoriaClient = categoriaClient;
    }

    @Override
    public String validarCategoriaExterno(long categoriaId) throws Exception {
        try {
            // 🚀 Usamos el cliente que ya configuramos
            // Si la categoría no existe, Feign lanzará un error y saltará el Fallback
            categoriaClient.buscarPorId(categoriaId);
            return "OK";
        } catch (Exception ex) {
            // Si falla la comunicación o no existe el ID, lanzamos el error para que el CB actúe
            throw new Exception("Error al validar categoría: " + ex.getMessage());
        }
    }

}

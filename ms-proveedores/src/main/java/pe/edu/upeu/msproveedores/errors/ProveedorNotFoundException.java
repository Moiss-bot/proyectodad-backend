package pe.edu.upeu.msproveedores.errors;

public class ProveedorNotFoundException extends RuntimeException {
    public ProveedorNotFoundException(Long id) {
        super("El Proveedor con el id " + id + " no existe");
    }
}

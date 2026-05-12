package pe.edu.upeu.msmelamine.errors;

public class MelamineNotFoundException extends RuntimeException {
    public MelamineNotFoundException(Long id) {
        super("La Melamina con el id " + id + " no existe.");
    }
}

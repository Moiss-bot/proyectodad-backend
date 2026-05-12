package pe.edu.upeu.msproveedores.repository;

import pe.edu.upeu.msproveedores.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity,Long> {
    // Método para la búsqueda por nombre (Like ignore case)
    List<ProveedorEntity> findByNombresContainingIgnoreCase(String nombres);
    Optional<ProveedorEntity> findByNombresAndApellidos(String nombres, String apellidos);
}

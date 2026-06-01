package pe.edu.upeu.msclientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msclientes.entity.ClienteEntity;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    List<ClienteEntity> findByNombreContainingIgnoreCase(String nombre);
    List<ClienteEntity> findByApellidoContainingIgnoreCase(String apellido);
}
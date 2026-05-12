package pe.edu.upeu.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.authservice.entity.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
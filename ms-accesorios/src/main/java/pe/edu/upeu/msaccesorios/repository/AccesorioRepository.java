package pe.edu.upeu.msaccesorios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msaccesorios.entity.AccesorioEntity;

import java.util.List;
import java.util.Optional;

public interface AccesorioRepository extends JpaRepository<AccesorioEntity, Long> {

    // 🎯 Mantiene la simetría exacta con 'findByNombreContainingIgnoreCase' de Clientes
    Optional<AccesorioEntity> findByNombreContainingIgnoreCase(String nombre);

    // 🎯 Mantiene la simetría con 'findByApellidoContainingIgnoreCase' de Clientes
    List<AccesorioEntity> findByCategoriaContainingIgnoreCase(String categoria);

    // 🎯 Métodos adicionales del módulo adaptados a tu estándar de persistencia
    List<AccesorioEntity> findByEstado(String estado);
    List<AccesorioEntity> findByStockGreaterThan(Integer stock);
}
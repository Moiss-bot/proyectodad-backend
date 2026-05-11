package com.example.ms_estado.repository;

import com.example.ms_estado.entity.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EstadoRepository extends JpaRepository<EstadoEntity, Long> {

    // Para buscar estados por nombre (ej: buscar "Act" y que traiga "Activo")
    List<EstadoEntity> findByNombreContainingIgnoreCase(String nombre);

    // Para buscar un estado exacto por su nombre
    Optional<EstadoEntity> findByNombre(String nombre);

}

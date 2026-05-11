package com.example.ms_categorias.repository;

import com.example.ms_categorias.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    List<CategoriaEntity> findByNombreContainingIgnoreCase(String nombre);
    Optional<CategoriaEntity> findByNombre(String nombre);

}

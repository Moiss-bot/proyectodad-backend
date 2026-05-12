package pe.edu.upeu.msmelamine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.msmelamine.entity.MelamineEntity;

@Repository
public interface MelamineRepository extends JpaRepository<MelamineEntity, Long> {
}

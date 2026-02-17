package com.PPOOII.Proyecto.Repository;

import com.PPOOII.Proyecto.Entities.Coordenadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {
    Optional<Coordenadas> findByPersonaId(Long personaId);
}


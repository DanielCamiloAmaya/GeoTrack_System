package com.PPOOII.Proyecto.Repository;

import com.PPOOII.Proyecto.Entities.CoordenadasHistoricas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadasHistoricasRepository
    extends JpaRepository<CoordenadasHistoricas, Long> {
    // aquí podrías agregar búsquedas por persona, rango de fechas, etc.
}


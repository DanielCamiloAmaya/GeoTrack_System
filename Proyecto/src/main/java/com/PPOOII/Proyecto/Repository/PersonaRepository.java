package com.PPOOII.Proyecto.Repository;

import com.PPOOII.Proyecto.Entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Búsqueda por número de identificación
    Persona findByIdentificacion(int identificacion);

    // Búsqueda por edad
    List<Persona> findByEdad(int edad);

    // Búsqueda por primer apellido
    List<Persona> findByPapellido(String papellido);

    // Búsqueda por primer nombre
    List<Persona> findByPnombre(String pnombre);
}

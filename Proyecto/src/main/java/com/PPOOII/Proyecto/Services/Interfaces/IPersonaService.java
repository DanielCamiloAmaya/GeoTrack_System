package com.PPOOII.Proyecto.Services.Interfaces;

import com.PPOOII.Proyecto.Entities.Persona;
import java.util.List;

public interface IPersonaService {
    // CRUD
    Persona crearPersona(Persona persona);
    Persona actualizarPersona(Persona persona);
    boolean eliminarPersona(Long id);
    List<Persona> listarPersonas();

    // Búsquedas
    Persona buscarPorId(Long id);
    Persona buscarPorIdentificacion(int identificacion);
    List<Persona> buscarPorEdad(int edad);
    List<Persona> buscarPorPrimerApellido(String papellido);
    List<Persona> buscarPorPrimerNombre(String pnombre);
}

package com.PPOOII.Proyecto.Services.Interfaces;

import java.util.Map;

import com.PPOOII.Proyecto.Entities.Usuario;

public interface IUsuarioService {
    Usuario obtenerUsuarioPorPersona(Long idpersona);
    boolean cambiarPassword(Long idpersona, String nuevoPassword);
    Map<String, Object> obtenerUsuarioPasswordApikey(Long idpersona);

}

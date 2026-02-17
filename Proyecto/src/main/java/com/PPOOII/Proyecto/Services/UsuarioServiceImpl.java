package com.PPOOII.Proyecto.Services;

import com.PPOOII.Proyecto.Entities.Usuario;
import com.PPOOII.Proyecto.Repository.UsuarioRepository;
import com.PPOOII.Proyecto.Services.Interfaces.IUsuarioService;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario obtenerUsuarioPorPersona(Long idpersona) {
        return usuarioRepository.findById(idpersona).orElse(null);
    }

    @Override
    public boolean cambiarPassword(Long idpersona, String nuevoPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idpersona);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setPassword(nuevoPassword);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> obtenerUsuarioPasswordApikey(Long idpersona) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idpersona);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Map<String, Object> credenciales = new HashMap<>();
            credenciales.put("usuario", usuario.getLogin());
            credenciales.put("password", usuario.getPassword());
            credenciales.put("apikey", usuario.getApikey());
            return credenciales;
        }
        return null;
    }

    // Método de la interfaz UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Suponiendo que tu repositorio retorna Optional<Usuario>
        Usuario appUser = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        

        // Roles vacíos, los agregas más adelante
        List<org.springframework.security.core.GrantedAuthority> authorities = new ArrayList<>();

        // Armas el UserDetails usando la clase User de Spring
        return new User(
                appUser.getLogin(),      // username
                appUser.getPassword(),   // password
                authorities              // lista de roles
        );
    }
}

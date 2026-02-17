package com.PPOOII.Proyecto.Repository;

import com.PPOOII.Proyecto.Entities.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    @Query("SELECT u FROM Usuario u WHERE u.login = :login AND u.apikey = :apikey")
    Optional<Usuario> findByLoginAndApikey(@Param("login") String login, @Param("apikey") String apikey);

}

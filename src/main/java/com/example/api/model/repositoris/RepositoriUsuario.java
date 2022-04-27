package com.example.api.model.repositoris;

import com.example.api.model.entitats.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoriUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}

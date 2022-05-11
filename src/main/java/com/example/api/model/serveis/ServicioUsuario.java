package com.example.api.model.serveis;

import com.example.api.model.entitats.Usuario;
import com.example.api.model.repositoris.RepositoriUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioUsuario {
    private final RepositoriUsuario repositoriUsuario;

    private final PasswordEncoder xifrat;

    public Usuario consultarPorUsername(String username) {
        return repositoriUsuario.findByUsername(username).orElse(null);
    }
    public Usuario crearNuevoUsuario(Usuario nouUsuari) {
        nouUsuari.setPassword(xifrat.encode(nouUsuari.getPassword()));
        repositoriUsuario.save(nouUsuari);
        return nouUsuari;
    }

    public List<Usuario> listarUsuarios(){
        return repositoriUsuario.findAll();
    }

    public UserDetails consultarPorId(Long id) {
        return repositoriUsuario.findById(id).orElse(null);
    }
}

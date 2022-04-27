package com.example.api.model.serveis;

import com.example.api.model.entitats.Usuario;
import com.example.api.model.repositoris.RepositoriUsuario;
import lombok.RequiredArgsConstructor;
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
    public Usuario crearNouUsuari(Usuario nouUsuari) {
        nouUsuari.setPassword(xifrat.encode(nouUsuari.getPassword()));
        repositoriUsuario.save(nouUsuari);
        return nouUsuari;
    }

    public List<Usuario> llistarUsuaris(){
        return repositoriUsuario.findAll();
    }

}

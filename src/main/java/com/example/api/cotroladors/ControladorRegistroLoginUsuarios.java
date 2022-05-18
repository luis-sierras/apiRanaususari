package com.example.api.cotroladors;

import com.example.api.model.entitats.Usuario;
import com.example.api.model.entitats.UsuarioDTO;
import com.example.api.model.serveis.ServicioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorRegistroLoginUsuarios {
    private final ServicioUsuario servicioUsuario;


    @GetMapping("/me")
    public UsuarioDTO consultaQuiEts(@AuthenticationPrincipal Usuario usu) {
        return new UsuarioDTO(usu.getUsername(), usu.getAvatar(), usu.getRol());
    }

    /*
    {
    "username":"Luis",
    "password":"123456789",
    "avatar":"http://imatge.com"
    }
    Afegeix id automàticament
     */
    @PostMapping("/usuaris")
    public ResponseEntity<?> nouUsuario(@RequestBody Usuario nouUsuario) {
        try {
            Usuario res = servicioUsuario.crearNuevoUsuario(nouUsuario);
            UsuarioDTO usu = new UsuarioDTO(res.getUsername(), res.getAvatar(), res.getRol());
            return new ResponseEntity<UsuarioDTO>(usu, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            //per si intentem afegir 2 usuaris amb el mateix username, saltarà excepció
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/usuaris")
    public ResponseEntity<?> llistarUsuariosDTO() {
        List<Usuario> res = servicioUsuario.listarUsuarios();
        List<UsuarioDTO> aux = new ArrayList();
        for (Usuario usu : res) {
            aux.add(new UsuarioDTO(usu.getUsername(), usu.getAvatar(), usu.getRol()));
        }
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(aux);
    }
}

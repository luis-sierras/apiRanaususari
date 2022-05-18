package com.example.api.cotroladors;

import com.example.api.model.entitats.Usuario;
import com.example.api.model.entitats.UsuarioDTO;
import com.example.api.model.serveis.ServicioUsuario;
import com.example.api.seguretat.JWT.JwtProvider;
import com.example.api.seguretat.JWT.LoginPassword;
import com.example.api.seguretat.JWT.UsuariJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UsuariJwt> login(@RequestBody LoginPassword userPassword) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassword.getUsername(), userPassword.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        Usuario usu = (Usuario) auth.getPrincipal();
        String jwtToken = tokenProvider.generateToken(auth);
        UsuariJwt uwu = new UsuariJwt(usu.getUsername(), usu.getAvatar(), usu.getRol(), jwtToken);
        //es retorna userName, Avatar, Rol i Token
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uwu);
    }


    @GetMapping("/login")
    public UsuarioDTO login(@AuthenticationPrincipal Usuario usu) {
        return new UsuarioDTO(usu.getUsername(), usu.getAvatar(), usu.getRol());
    }




    @GetMapping("/me")
    public UsuarioDTO consultaQuiEts(@AuthenticationPrincipal Usuario usu) {
        return new UsuarioDTO(usu.getUsername(), usu.getAvatar(), usu.getRol());
    }


//    {
//    "username":"LuisJWT",
//    "password":"1234JWT",
//    "avatar":"http://imatge.com"
//    }
//    Afegeix id automàticament

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

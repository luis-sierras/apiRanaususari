package com.example.api.cotroladors;

import com.example.api.model.entitats.Rana;
import com.example.api.model.serveis.ServeiRanas;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorResponseEntity {
    private final ServeiRanas serveiRanas;

    @GetMapping("/ranas")
    public ResponseEntity<?> consultarRana() {
        List<Rana> res = serveiRanas.listarRanas();
        if (res == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(res);
    }

    @PostMapping("/ranas")
    public ResponseEntity<?> afegirRana(@RequestBody Rana rana) {
        try {
            serveiRanas.afegirRana(rana);
            return new ResponseEntity<Rana>(rana, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/ranas/{id}")
    public ResponseEntity<?> consultarRana(@PathVariable int id) {
        Rana res = serveiRanas.consultarRana(id);
        if (res == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(res);
    }

    @PutMapping("/ranas")
    public ResponseEntity<?> modificarVideojoc(@RequestBody Rana rana) {
        Rana res = serveiRanas.modificarRana(rana);
        if (res != null) return ResponseEntity.ok(res);
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/ranas/{id}")
    public ResponseEntity<?> eliminarRana(@PathVariable int id) {
        Rana res = serveiRanas.eliminarRana(id);
        if (res != null) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}

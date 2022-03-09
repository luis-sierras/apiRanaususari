package com.example.api.cotroladors;

import com.example.api.model.entitats.Rana;
import com.example.api.model.serveis.ServeiRanas;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorResponseEntity {
    private final ServeiRanas serveiRanas;

    @GetMapping("/rana/{id}")
    public ResponseEntity<?> consultarRana() {
        List<Rana> res = serveiRanas.listarRanas();
        if (res == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(res);
    }

    //proves amb un altre endpoint per consultar usuari
    //si l'id d'usuari no existeix es retorna 404 Not Found
    @GetMapping("/rana/{id}")
    public ResponseEntity<?> consultarRana(@PathVariable int id) {
        Rana res = serveiRanas.consultarRana(id);
        if (res == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(res);
    }

    //proves amb un altre endpoint per crear un usuari
    //si es pot crear es retorna CREATED
    @PostMapping("/user")
    public ResponseEntity<?> crearUsusari(@RequestBody Rana nou){
        Rana res=serveiRanas.afegirRana(nou);
        return new ResponseEntity<Rana>(res, HttpStatus.CREATED);
    }
}

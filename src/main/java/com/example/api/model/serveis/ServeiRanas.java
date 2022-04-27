package com.example.api.model.serveis;

import com.example.api.model.entitats.Rana;
import com.example.api.model.repositoris.RepositoriRanas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiRanas {
    private final RepositoriRanas repositoriRanas;

    //listar todas las ranas
    public List<Rana> listarRanas(){
        return repositoriRanas.findAll();
    }

    //consultar ranas por id
    public Rana consultarRana(int id){
        return repositoriRanas.findById(id).orElse(null);
    }

    //agregar rana
    public Rana afegirRana(Rana it){
        return repositoriRanas.save(it);
    }

    //modificar sencer, si existeix el canvia, sino retorna null
    public Rana modificarRana(Rana rana){
        Rana aux=null;
        if(repositoriRanas.existsById(rana.getId())) aux=repositoriRanas.save(rana);
        return aux;
    }

    //eliminar rana per id
    //si no existeix id retorna null
    public Rana eliminarRana(int id){
        Rana res=repositoriRanas.findById(id).orElse(null);
        if(res!=null) repositoriRanas.deleteById(id);
        return res;
    }

    public void añadirRana(Rana rana) {

    }
}

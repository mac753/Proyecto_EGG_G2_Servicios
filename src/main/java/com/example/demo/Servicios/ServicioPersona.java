package com.example.demo.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repositorio.personaRepositorio;
import com.example.demo.entidades.Persona;

@Service
public class ServicioPersona {

    @Autowired
    personaRepositorio personaRepositorio;

    public List<Persona> ListarPersonas() {
        List<Persona> personas = personaRepositorio.findAll();
        return personas;
    }

}

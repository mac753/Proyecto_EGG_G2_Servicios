package com.example.demo.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Enumeraciones.Estado;
import com.example.demo.Repositorio.personaRepositorio;
import com.example.demo.entidades.Persona;

import jakarta.transaction.Transactional;

@Service
public class ServicioPersona {

    @Autowired
    personaRepositorio personaRepositorio;

    public List<Persona> ListarPersonas() {
        List<Persona> personas = personaRepositorio.findAll();
        return personas;
    }

    @Transactional
    public void cambiarEstado(String id) {
        Optional<Persona> respuesta = personaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Persona persona = respuesta.get();
            if (persona.getEstado().equals(Estado.ACTIVO)) {
                persona.setEstado(Estado.INACTIVO);
            } else if (persona.getEstado().equals(Estado.INACTIVO)) {
                persona.setEstado(Estado.ACTIVO);
            }
        }
    }
}

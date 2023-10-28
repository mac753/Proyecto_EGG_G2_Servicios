package com.example.demo.Servicios;

import com.example.demo.Repositorio.personaRepositorio;
import com.example.demo.entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Service
public class ServicioPersona implements UserDetailsService{

    @Autowired
    personaRepositorio personaRepositorio;

    public List<Persona> ListarPersonas() {
        List<Persona> personas = personaRepositorio.findAll();
        return personas;
    }

    @Override
    
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona persona = personaRepositorio.buscarPersonarPorEmail(email);
        if (persona != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("personasession", persona);
            //session.setAttribute("personasession", persona);
            return new User(persona.getEmail(), persona.getPassword(), permisos);
        } else {
            return null;
        }

    }
    }



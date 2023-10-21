package com.example.demo.Servicios;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Enumeraciones.Rol;
import com.example.demo.Repositorio.UsuarioRepositorio;
import com.example.demo.Repositorio.personaRepositorio;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.Excepciones.MiException;
import com.example.demo.entidades.Persona;

import com.example.demo.entidades.Usuario;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private personaRepositorio personaRepositorio;

    @Transactional
    public void crearUsuario(String nombre, String email, String password, String password2, Long telefono,
            String direccion)
            throws MiException {
        validar(nombre, email, password, password2, telefono, direccion);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRol(Rol.USER);
        usuarioRepositorio.save(usuario);

    }

    private void validar(String nombre, String email, String password, String password2, Long telefono,
            String direccion)
            throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("El password no puede estar vacio, o ser nulo");
        }
        if (password.isEmpty()) {
            throw new MiException("El password no puede estar vacio");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (direccion.isEmpty()) {
            throw new MiException("la direccion no puede estar vacia");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona persona = personaRepositorio.buscarPersonarPorEmail(email);
        if (persona != null) {
            List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("personasession", persona);
            return new User(persona.getEmail(), persona.getPassword(), permisos);
        } else {
            return null;
        }

    }

}
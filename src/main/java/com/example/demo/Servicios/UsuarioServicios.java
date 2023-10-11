
package com.example.demo.Servicios;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.UsuarioRepositorio;
import com.example.demo.entidades.Usuario;
import com.example.demo.enume.Rol;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServicios {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String nombre, String email, String password, Long telefono,
            String direccion) throws MiException {
        validar(nombre, email, password, telefono, direccion);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificarUsuario(Long id, String nombre, String email, String password, Long telefono,
            String direccion) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);

            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(Long id) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            usuarioRepositorio.deleteById(id);
        }
    }

    public List<Usuario> ListarUsuarios() {

        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    public void validar(String nombre, String email, String password, Long telefono, String direccion) throws MiException {

        //validar(nombre, email, password, telefono,direccion);
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio o ser nulo");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede estar vacio o ser nulo");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede estar vacio o tener longitud menor a 6 caracteres");
        }

        if (telefono == null) {
            throw new MiException("El telefono no puede estar vacio o ser nulo");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("El campo direccion no puede estar vacio o ser nulo");
        }

    }

    public Usuario getOne(Long id) {
        return usuarioRepositorio.getOne(id);
    }

}

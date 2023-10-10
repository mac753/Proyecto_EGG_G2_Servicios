package com.example.demo.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Excepciones.MiException;
import com.example.demo.entidades.Proovedor;
import com.example.demo.enume.Rol;
import com.example.demo.Repositorio.proveedorRepositorio;

import jakarta.transaction.Transactional;

@Service
public class proveedorServicio {

    @Autowired
    private proveedorRepositorio proveedorRepositorio;

    @Transactional
    public void crearProveedor(String nombre, String email, String password, Long telefono,
            String comentarios, String direccion, float honorarioHoras, Integer cantidadContactos) throws MiException {

        validar(nombre, email, password, telefono, comentarios, direccion, honorarioHoras, cantidadContactos);
        Proovedor proveedor = new Proovedor();
        // proveedor.setId(id);
        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setPassword(password);
        proveedor.setTelefono(telefono);
        proveedor.setComentarios(comentarios);
        proveedor.setDireccion(direccion);
        proveedor.setHonorarioHora(honorarioHoras);
        proveedor.setCantidadContactos(cantidadContactos);

        proveedorRepositorio.save(proveedor);
    }

    public List<Proovedor> listarProveedor() {

        List<Proovedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

    /*
     * public void modificarProveedor(String nombre, String email, String password,
     * Integer telefono,
     * String comentarios, String direccion, float honorarioHoras, Integer
     * cantidadContactos) throws MiException {
     * 
     * validar(nombre, email, password, telefono, comentarios, direccion,
     * honorarioHoras, cantidadContactos);
     * Optional<Proovedor> respuesta = proveedorRepositorio.findById(id);
     * 
     * if (respuesta.isPresent()) {
     * 
     * Proovedor proovedor = respuesta.get();
     * 
     * // proovedor.setId(id);
     * proovedor.setNombre(nombre);
     * proovedor.setEmail(email);
     * proovedor.setRol(Rol.PROVEEDOR);
     * proovedor.setPassword(password);
     * proovedor.setTelefono(telefono);
     * proovedor.setComentarios(comentarios);
     * proovedor.setDireccion(direccion);
     * proovedor.setHonorarioHora(honorarioHoras);
     * proovedor.setCantidadContactos(cantidadContactos);
     * proveedorRepositorio.save(proovedor);
     * }
     * }
     */
    private void validar(String nombre, String email, String password, Long telefono,
            String comentarios, String direccion, float honorarioHoras, Integer cantidadContactos) throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (password.isEmpty()) {
            throw new MiException("El password no puede estar vacio");
        }
        if (telefono == null) {
            throw new MiException("El telfono no puede ser nulo");
        }
        if (comentarios.isEmpty()) {
            throw new MiException("El comentario no puede estar vacio");
        }
        if (direccion.isEmpty()) {
            throw new MiException("La direccion no puede estar vacia");
        }
        if (honorarioHoras < 0) {
            throw new MiException("debes actualizar a un honorario por hora valido");
        }
        if (cantidadContactos == null) {
            throw new MiException("Ingrese la cantidad de contactos");
        }

    }
}

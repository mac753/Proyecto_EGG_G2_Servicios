package com.example.demo.Servicios;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.Enumeraciones.Rol;
import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.entidades.Proveedor;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class proveedorServicio implements UserDetailsService {

    @Autowired
    private proveedorRepositorio proveedorRepositorio;

    @Transactional
    public void crearProveedor(String nombre, String email, String password, String password2, Long telefono,
            String direccion, float honorarioHoras, String rubro, String presentacion)
            throws MiException {

        validar(nombre, email, password, password2, telefono, direccion, honorarioHoras, rubro, presentacion);
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setHonorarioHora(honorarioHoras);
        proveedor.setRubro(rubro);
        proveedor.setPresentacion(presentacion);

        proveedorRepositorio.save(proveedor);
    }

    public List<Proveedor> listarProveedor() {

        List<Proveedor> proveedores = new ArrayList<Proveedor>();

        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

    public List<Proveedor> listarProveedorPorRubro(String rubro) {

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores = (List<Proveedor>) proveedorRepositorio.buscarProveedorPorRubro(rubro);
        return proveedores;
    }

    /*
     * public void modificarProveedor(String nombre, String email, String password,
     * Integer telefono,
     * String direccion, float honorarioHoras,
     * ) throws MiException {
     * 
     * validar(nombre, email, password, telefono, comentarios, direccion,
     * honorarioHoras);
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
    private void validar(String nombre, String email, String password, String password2, Long telefono,
            String direccion, float honorarioHoras, String rubro, String presentacion)
            throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (password.isEmpty()) {
            throw new MiException("El password no puede estar vacio");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (telefono == null) {
            throw new MiException("El telfono no puede ser nulo");
        }

        if (direccion.isEmpty()) {
            throw new MiException("La direccion no puede estar vacia");
        }
        if (honorarioHoras < 0) {
            throw new MiException("debes actualizar a un honorario por hora valido");
        }
        if (rubro.isEmpty()) {
            throw new MiException("El rubro no puede estar vacia");
        }
        if (presentacion.isEmpty()) {
            throw new MiException("La presentacion no puede estar vacia");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarProveedorPorEmail(email);
        if (proveedor != null) {
            List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", proveedor);
            return new User(proveedor.getNombre(), proveedor.getPassword(), permisos);

        } else {
            return null;
        }
    }

}

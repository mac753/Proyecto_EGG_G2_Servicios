package com.example.demo.proveedorServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.example.demo.Excepciones.MiException;
import com.example.demo.entidades.Proovedor;
import com.example.demo.entidades.Usuario;
import com.example.demo.enume.Rol;
import com.example.demo.personaRepo.proveedorRepositorio;

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
        Proovedor proveedor = new Proovedor();

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

    public List<Proovedor> listarProveedor() {

        List<Proovedor> proveedores = new ArrayList<Proovedor>();

        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

    public List<Proovedor> listarProveedorPorRubro(String rubro) {

        List<Proovedor> proveedores = new ArrayList<Proovedor>();
        proveedores = (List<Proovedor>) proveedorRepositorio.buscarProveedorPorRubro(rubro);
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
      public UserDetails loadUserByUsername(String email) throws
      UsernameNotFoundException {
      Proovedor proveedor = proveedorRepositorio.buscarProveedorPorEmail(email);
      if (proveedor != null) {
      List<GrantedAuthority> permisos = new ArrayList();
      GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
      proveedor.getRol().toString());
      permisos.add(p);
       ServletRequestAttributes attr = (ServletRequestAttributes)
       RequestContextHolder.currentRequestAttributes();
       HttpSession session = attr.getRequest().getSession(true);
       session.setAttribute("proveedorsession", proveedor);
      // return (UserDetails) new User(usuario.getEmail(), usuario.getPassword(),
      // permisos);
      return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
      } else {
      return null;
      }
      }

}

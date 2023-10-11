
package com.example.demo.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Servicios.UsuarioServicios;
import com.example.demo.Servicios.proveedorServicio;




@Controller

@RequestMapping("/usuario") // localhost:8080/usuario
public class UsuarioControlador {

    @Autowired
    private UsuarioServicios UsuarioServicio;

    @GetMapping("/registrar") // localhost:8080/proveedor/registrar
    public String registrar() {
        return "contacto.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, String email, @RequestParam String password,
            Long telefono,
            String direccion) {

        try {
            UsuarioServicio.crearUsuario(nombre, email, password, telefono, direccion);
        } catch (MiException ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);

            return "contacto.html";
        }

        return "redirect:/";
    }
    
}

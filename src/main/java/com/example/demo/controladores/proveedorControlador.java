package com.example.demo.controladores;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.logging.Level;
import com.example.demo.Excepciones.MiException;
import com.example.demo.proveedorServicio.proveedorServicio;

@Controller
@RequestMapping("/proveedor") // localhost:8080/proveedor
public class proveedorControlador {

    @Autowired
    private proveedorServicio proveedorServicio;

    @GetMapping("/registrar") // localhost:8080/proveedor/registrar
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, String email, @RequestParam String password,
            Long telefono,
            String comentarios, String direccion, float honorarioHoras, Integer cantidadContactos) {

        try {
            proveedorServicio.crearProveedor(nombre, email, password, telefono, comentarios, direccion,
                    honorarioHoras, cantidadContactos);
        } catch (MiException ex) {
            Logger.getLogger(proveedorControlador.class.getName()).log(Level.SEVERE, null, ex);

            return "registro.html";
        }

        return "redirect:/";
    }
}

package com.example.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Servicios.UsuarioServicio;
import com.example.demo.entidades.Persona;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registroUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Long telefono, @RequestParam String direccion,
            ModelMap modelo) {

        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2, telefono, direccion);
            modelo.put("exito", "Usuario registrado correcamente !");

            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registroUsuario.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos!");
        }
        return "LoginUsuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Persona logueado = (Persona) session.getAttribute("personasession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        if (logueado.getRol().toString().equals("PROVEEDOR")) {
            return "redirect:/proveedor/vistaProveedor";
        }
        return "Buscador.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/buscador")
    public String inicio() {

        return "Buscador.html";
    }

    @GetMapping("/conocenos")
    public String conocenos() {

        return "conocenos.html";
    }
}

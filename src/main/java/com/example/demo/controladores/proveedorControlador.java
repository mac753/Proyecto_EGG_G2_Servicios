package com.example.demo.controladores;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Level;
import com.example.demo.Excepciones.MiException;
import com.example.demo.entidades.Proovedor;
import com.example.demo.entidades.Usuario;
import com.example.demo.personaRepo.proveedorRepositorio;
import com.example.demo.proveedorServicio.proveedorServicio;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/proveedor") // localhost:8080/proveedor
public class proveedorControlador {

    @Autowired
    private proveedorServicio proveedorServicio;
    @Autowired
    private proveedorRepositorio proveedorRepositorio;

    @GetMapping("/registrar") // localhost:8080/proveedor/registrar
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, String email, @RequestParam String password,
            Long telefono, String password2,
            String direccion, float honorarioHoras, String rubro, String presentacion, ModelMap modelo,
            String rubroOtro) {

        try {
            proveedorServicio.crearProveedor(nombre, email, password, password2, telefono, direccion,
                    honorarioHoras, rubro, presentacion);
            modelo.put("exito", "Usuario registrado correcamente !");

            String rubroSeleccionado = rubro;
            if (rubroSeleccionado.equals("Otro")) {
                rubroSeleccionado = rubroOtro;
            }
            return "index.html";
        } catch (MiException ex) {
            // Logger.getLogger(proveedorControlador.class.getName()).log(Level.SEVERE,
            // null, ex);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";

        }

        // return "redirect:/";
    }

    @GetMapping("/login") // http://localhost:8080/login -> va a guiar al loguin
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpServletRequest request) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos!");
        }

        return "loginUsuario.html";
    }
    /*
     * @PreAuthorize("hasRole('ROLE_PROVEEDOR', 'ROLE_USER')")
     * 
     * @GetMapping("/inicioProveedor")
     * public String inicioProveedor() {
     * 
     * return "vistaProveedor.html";
     * }
     */

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/buscarProveedorPorRubro") // localhost:8080/proveedor/buscarProveedorPorRubro -> busca al proveedor
                                            // por rubro
    public String buscarProveedorPorRubro(@RequestParam String rubro, ModelMap model) {
        List<Proovedor> proveedores = proveedorRepositorio.buscarProveedorPorRubro(rubro);
        model.addAttribute("proveedores", proveedores);
        return "Buscador";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Proovedor logueado = (Proovedor) session.getAttribute("proveedorsession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        if (logueado.getRol().toString().equals("PROVEEDOR")) {
            return "redirect:/vistaProveedor";
        }
        return "vistaUsuario.html";
    }

    @GetMapping("/vistaProveedor")
    public String panelProveedor() {

        return "vistaProveedor.html";
    }

}

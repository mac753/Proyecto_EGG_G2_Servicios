package com.example.demo.controladores;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.Servicios.proveedorServicio;

import com.example.demo.entidades.Proveedor;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entidades.Usuario;

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
        return "registroProveedor.html";
    }

    @PostMapping("/registro")
    public String registro(String nombre, String email, @RequestParam String password,
            Long telefono, String password2,
            String direccion, float honorarioHoras, String rubro, String presentacion, ModelMap modelo,
            String rubroOtro, MultipartFile archivo) throws IOException {

        try {
            proveedorServicio.crearProveedor(archivo, nombre, email, password, password2, telefono, direccion,
                    honorarioHoras, rubro, presentacion);
            modelo.put("exito", "Usuario registrado correctamente !");

            String rubroSeleccionado = rubro;
            if (rubroSeleccionado.equals("Otro")) {
                rubroSeleccionado = rubroOtro;
            }
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registroProveedor.html";

        }
    }

    @GetMapping("/buscador")
    public String buscador() {
        return "Buscador";
    }

    @PostMapping("/buscador") // localhost:8080/proveedor/buscarProveedorPorRubro -> busca al proveedor
    // por rubro
    public String buscador(@RequestParam String rubro, ModelMap model) {
        List<Proveedor> proveedores = proveedorRepositorio.buscarProveedorPorRubro(rubro);
        model.addAttribute("proveedores", proveedores);
        return "Buscador";
    }


   @GetMapping("/panelProveedor")
    public String panelProveedor(HttpSession session, ModelMap modelo) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        if (proveedor != null) {
            // Aquí tienes acceso al proveedor y sus datos
            modelo.addAttribute("proveedor", proveedor);
        } else {
            // Manejar la situación en la que el proveedor no está en la sesión
        }

        return "panelProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')") // ver si lo podemos sacar
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Proveedor logueado = proveedorRepositorio.buscarProveedorPorEmail(authentication.getName());
        modelo.put("proveedor", logueado);
        return "modificarProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable Long id, String nombre, String email,
            @RequestParam String password,
            @RequestParam String password2,
            Long telefono, String direccion, float honorarioHora, String rubro, String presentacion,
            ModelMap modelo) {

        try {
            proveedorServicio.actualizar(archivo, id, nombre, email, password, password2, telefono, direccion,
                    honorarioHora, rubro, presentacion);

            modelo.put("exito", "Proveedor actualizado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            System.out.println("No se esta modificando");
            return "modificarProveedor.html";
        }

    }


    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }
}


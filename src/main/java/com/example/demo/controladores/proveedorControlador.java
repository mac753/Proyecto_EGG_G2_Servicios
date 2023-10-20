package com.example.demo.controladores;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.Servicios.proveedorServicio;

import com.example.demo.entidades.Proveedor;

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

    @GetMapping("/vistaProveedor")
    public String panelProveedor() {

        return "panelProveedor.html";
    }

}

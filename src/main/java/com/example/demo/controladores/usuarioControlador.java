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
import com.example.demo.proveedorServicio.proveedorServicio;
import com.example.demo.proveedorServicio.usuarioServicio;
/*
 * @Controller
 * 
 * @RequestMapping("/usuario") // localhost:8080/usuario
 * public class usuarioControlador {
 * 
 * @Autowired
 * private usuarioServicio usuarioServicio;
 * 
 * @GetMapping("/registrar") // localhost:8080/usuario/registrar
 * public String registrar() {
 * return "registro.html";// hay que hacer una vista para esto
 * }
 * 
 * @PostMapping("/registro")
 * public String registro(String nombre, String email, @RequestParam String
 * password,
 * Long telefono,
 * String direccion) {
 * 
 * try {
 * usuarioServicio.crearUsuario(nombre, email, password, password2, telefono,
 * direccion);
 * } catch (MiException ex) {
 * Logger.getLogger(usuarioControlador.class.getName()).log(Level.SEVERE, null,
 * ex);
 * 
 * return "registro.html";
 * }
 * 
 * return "redirect:/";
 * }
 * }
 */

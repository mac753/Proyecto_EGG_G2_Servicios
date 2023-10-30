
package com.example.demo.controladores;

import com.example.demo.Enumeraciones.EstadoOrdenTrabajo;
import com.example.demo.Repositorio.OrdenTrabajoRepositorio;
import com.example.demo.Servicios.OrdenTrabajoServicio;
import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Persona;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orden")
public class OrdenTrabajoControlador {

    @Autowired
    OrdenTrabajoServicio ots;

    @Autowired
    proveedorServicio ps;

    @Autowired
    OrdenTrabajoRepositorio otr;

    @GetMapping("/contacto/{idproveedor}")
    public String contactar(@PathVariable Long idproveedor, ModelMap modelo) {
        modelo.addAttribute("proveedor", ps.buscarPorid(idproveedor));
        return "ContactarProveedor.html";
    }

    @PostMapping("/crearOrden")
    public String crearOrden(HttpSession session, @RequestParam Long idproveedor, @RequestParam String comentario) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.crearOt(idproveedor, idusuario, comentario);
        return "redirect:/orden/ordenes";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PROVEEDOR')")
    @GetMapping("/ordenes")
    public String listarOrdenes(HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        System.out.println(logueado.getRol().toString());
        
        if (logueado.getRol().toString().equals("USER")) {
            System.out.println("entre en el user");
            List<OrdenTrabajo> listaOrdenes = ots.ListarOrdenesTrabajo(idusuario);
            modelo.addAttribute("listaOrdenes", listaOrdenes);
            return "MisOrdenesUsuario.html"; // Vista para usuarios      
            
        } 
        if (logueado.getRol().toString().equals("PROVEEDOR")) {
            System.out.println("entre en el proveedor");
            System.out.println(logueado.getId().toString());
            List<OrdenTrabajo> listaOrdenesProveedor = ots.ListarOrdenesTrabajoProveedor(idusuario);
            modelo.addAttribute("listaOrdenes", listaOrdenesProveedor);
            return "MisOrdenes.html"; // Vista para proveedores}
            
        }
    
        // Manejar cualquier otro caso si es necesario
        return "error.html";
    }

@GetMapping("/cancelar/{id}")
    public String cancelarOrden(@PathVariable Long id){
        ots.cancelarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
        }

    
    @GetMapping("/aceptar/{id}")
    public String aceptarOrden(@PathVariable Long id){
        ots.aceptarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
         }
    

    @GetMapping("/calificar")
    public String calificarOrden(@PathVariable Long id) {
        // ots.aceptarOrdenTrabajo(id);
        return "Calificar.html";
    }
    
     @GetMapping("/cotizacion/{id}")
    public String cotizar(@PathVariable Long id, Long valor){
        //ots.aceptarOrdenTrabajo(id);
        return "Calificar.html";
    }



    @PostMapping("/aceptar/{id}")
    public String aceptarOrden(@PathVariable Long id, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        //ots.aceptarOrdenTrabajo(id, idusuario);
        return "redirect:/orden/ordenes";
    }

    @GetMapping("/finalizar")
    public String finalizarOrden(@PathVariable Long id) {
        ots.finalizarOrdenTrabajo(id, id);
        return "redirect:/orden/ordenes";
    }

    @PostMapping("/finalizar/{id}")
    public String finalizarOrden(@PathVariable Long id, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.finalizarOrdenTrabajo(id, idusuario);
        return "redirect:/orden/ordenes";
    }


    
}

    

    
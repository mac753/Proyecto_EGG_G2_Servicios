
package com.example.demo.controladores;

import com.example.demo.Servicios.OrdenTrabajoServicio;
import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Persona;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/ordenes")
    public String listarOrdenes(HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        System.out.println(logueado.getRol().toString());
        
        if (logueado.getRol().toString().equals("USER")) {
            System.out.println("entre en el user");
            List<OrdenTrabajo> listaOrdenes = ots.ListarOrdenesTrabajo(idusuario);
             modelo.addAttribute("listaOrdenes", listaOrdenes);
            
        } 
        if (logueado.getRol().toString().equals("PROVEEDOR")) {
            System.out.println("entre en el proveedor");
            System.out.println(logueado.getId().toString());
            List<OrdenTrabajo> listaOrdenesProveedor = ots.ListarOrdenesTrabajoProveedor(idusuario);
            modelo.addAttribute("listaOrdenes", listaOrdenesProveedor);
            
        }

        
       return "MisOrdenes.html";
        
                
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
    
    @GetMapping("/calificar/{id}")
    public String calificarOrden(@PathVariable Long id){
        //ots.aceptarOrdenTrabajo(id);
        return "Calificar.html";
    }
    
     @GetMapping("/cotizacion/{id}")
    public String cotizar(@PathVariable Long id, Long valor){
        //ots.aceptarOrdenTrabajo(id);
        return "Calificar.html";
    }
    
    
//    @PostMapping("/cancelar/{id}")
//    public String cancelarOrdene(@PathVariable Long id){
//        ots.cancelarOrdenTrabajo(id);
//        return "redirect:/orden/ordenes";
//    }

}

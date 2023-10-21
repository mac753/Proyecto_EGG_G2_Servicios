
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "MisOrdenes.html";
    }

    @GetMapping("/ordenes")
    public String listarOrdenes(@RequestParam Long idpersona, ModelMap modelo) {
        List<OrdenTrabajo> listaOrdenes = ots.ListarOrdenesTrabajo(idpersona);
        modelo.addAttribute("listaOrdenes", listaOrdenes);
        return "inicio.html";
    }

}

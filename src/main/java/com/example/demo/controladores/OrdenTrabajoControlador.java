
package com.example.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orden")
public class OrdenTrabajoControlador {

    
@GetMapping("/crear")    
public String crearOrden(){
    return "ContactarProveedor.html";
}

    
}

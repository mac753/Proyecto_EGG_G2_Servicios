
package com.example.demo.Repositorio;

import com.example.demo.entidades.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepositorio extends JpaRepository<OrdenTrabajo,Long> {


    
}

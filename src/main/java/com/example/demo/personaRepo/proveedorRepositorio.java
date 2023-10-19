package com.example.demo.personaRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Proovedor;

@Repository
public interface proveedorRepositorio extends JpaRepository<Proovedor, String> {

    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public Proovedor buscarProveedorPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Persona p WHERE p.rubro =:rubro")
    public List<Proovedor> buscarProveedorPorRubro(@Param("rubro") String rubro);

}

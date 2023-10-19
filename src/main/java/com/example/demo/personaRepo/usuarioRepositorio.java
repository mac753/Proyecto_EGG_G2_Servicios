package com.example.demo.personaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Usuario;

@Repository
public interface usuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public Usuario BuscarUsuarioPorEmail(@Param("email") String email);
}

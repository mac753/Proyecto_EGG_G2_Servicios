package com.example.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Proovedor;

@Repository
public interface proveedorRepositorio extends JpaRepository<Proovedor, Long> {

    @Query("SELECT u FROM Proovedor u WHERE u.direccion =:direccion")
    public Proovedor buscarPorDireccion(@Param("direccion") String direccion);

}

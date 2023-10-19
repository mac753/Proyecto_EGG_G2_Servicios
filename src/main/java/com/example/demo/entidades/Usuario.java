package com.example.demo.entidades;

import com.example.demo.enume.Rol;

import jakarta.persistence.Entity;

@Entity
public class Usuario extends Persona {

    private String direccion;

    public Usuario() {

    }

    public Usuario(Long id, String nombre, String email, Rol rol, String password, Long telefono, String direccion) {
        super();
        this.direccion = direccion;
    }

    public Usuario(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario [direccion=" + direccion + "]";
    }

}

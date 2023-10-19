package com.example.demo.entidades;

import java.util.List;

import com.example.demo.enume.Rol;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;

@Entity
public class Proovedor extends Persona {

    private String direccion;
    private float honorarioHora;
    private String rubro;
    private String presentacion;

    public Proovedor() {

    }

    public Proovedor(String nombre, String email, Rol rol, String password, Long telefono, String direccion,
            float honorarioHora, String rubro, String presentacion) {
        super();
        this.direccion = direccion;
        this.honorarioHora = honorarioHora;
        this.rubro = rubro;
        this.presentacion = presentacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getHonorarioHora() {
        return honorarioHora;
    }

    public void setHonorarioHora(float honorarioHora) {
        this.honorarioHora = honorarioHora;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public String toString() {
        return "Proovedor [direccion=" + direccion + ", honorarioHora=" + honorarioHora + ", rubro=" + rubro
                + ", presentacion=" + presentacion + "]";
    }

}

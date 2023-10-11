package com.example.demo.entidades;

import jakarta.persistence.Entity;

@Entity
public class Proovedor extends Persona {

    
    private String direccion;
    private float honorarioHora;
    private Integer cantidadContactos;

    public Proovedor() {

    }

    public Proovedor(String direccion, float honorarioHora, Integer cantidadContactos) {

        super();
        
        this.direccion = direccion;
        this.honorarioHora = honorarioHora;
        this.cantidadContactos = cantidadContactos;
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

    public Integer getCantidadContactos() {
        return cantidadContactos;
    }

    public void setCantidadContactos(Integer cantidadContactos) {
        this.cantidadContactos = cantidadContactos;
    }

    @Override
    public String toString() {
        return "Proovedor [direccion=" + direccion + ", honorarioHora=" + honorarioHora + ", cantidadContactos="
                + cantidadContactos + "]";
    }

   
    


    }



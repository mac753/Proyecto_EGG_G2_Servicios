
package com.example.demo.entidades;

import com.example.demo.enume.EstadoOrdenTrabajo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rubro;
    private Integer puntaje;
    private double valor;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenTrabajo estadOrden;
    @OneToOne
    private Proovedor proveedor;
    @OneToOne
    private Usuario usuario;
    private String comentario;

    public OrdenTrabajo() {
    }



    public OrdenTrabajo(Long id, String rubro, Integer puntaje, double valor,EstadoOrdenTrabajo estadOrden, Proovedor proveedor,
            Usuario usuario, String comentario) {
        this.id = id;
        this.rubro = rubro;
        this.puntaje = puntaje;
        this.valor = valor;
        this.estadOrden = estadOrden;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public EstadoOrdenTrabajo getEstadOrden() {
        return estadOrden;
    }

    public void setEstadOrden(EstadoOrdenTrabajo estadOrden) {
        this.estadOrden = estadOrden;
    }

    public Proovedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proovedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }



    public Integer getPuntaje() {
        return puntaje;
    }



    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
    
    

    
}

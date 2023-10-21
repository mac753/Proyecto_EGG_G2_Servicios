
package com.example.demo.Servicios;

import com.example.demo.Enumeraciones.EstadoOrdenTrabajo;
import com.example.demo.Repositorio.OrdenTrabajoRepositorio;
import com.example.demo.Repositorio.UsuarioRepositorio;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.entidades.OrdenTrabajo;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenTrabajoServicio {

    @Autowired
    OrdenTrabajoRepositorio otRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    proveedorRepositorio proveedorRepositorio;

    @Transactional // crear orden de trabajo en estado cotizando para el contacto.
    public void crearOt(Long idProveedor, Long idUsuario, String comentario) {
        OrdenTrabajo ordentrabajo = new OrdenTrabajo();
        ordentrabajo.setProveedor(proveedorRepositorio.findById(idProveedor).get());
        ordentrabajo.setUsuario(usuarioRepositorio.findById(idUsuario).get());
        ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.COTIZANDO);
        ordentrabajo.setComentario(comentario);// necesito pintar zzzz
        otRepositorio.save(ordentrabajo);
    }

    @Transactional // Servicio para asignar el valor por parte del proveedor
    public void asignarValor(Long idOrdenTrabajo, Long idProveedor, double valor) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {
            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setValor(0);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario acepta la cotizacion e inicia la orden
    public void aceptarOrdenTrabajo(Long idOrdenTrabajo, Long idUsuario) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.ACTIVA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario cancela orden de servicio
    public void cancelarOrdenTrabajo(Long idOrdenTrabajo, Long idUsuario) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.CANCELADA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El proveedor confirma la finalizacion del servicio
    public void finalizarOrdenTrabajo(Long idOrdenTrabajo, Long idProveedor) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.FINALIZADA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario califica la orden del servicio
    public void calificarOrdenTrabajo(Long idOrdenTrabajo, Long idUsuario, String comentario, Integer puntaje) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);
        // agregar condicional si se califica solo si está finalizada
        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setComentario(comentario);
            ordentrabajo.setPuntaje(puntaje);
            // ordentrabajo.getProveedor().setPuntaje
            otRepositorio.save(ordentrabajo);
        }
    }

    public List<OrdenTrabajo> ListarOrdenesTrabajo(Long idPersona) {

        List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
        ordenesTrabajo = otRepositorio.buscarPorid(idPersona);
        return ordenesTrabajo;
    }

}

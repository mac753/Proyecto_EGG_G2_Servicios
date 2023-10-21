package com.example.demo.Servicios;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.ImagenRepositorio;
import com.example.demo.entidades.Imagen;

@Service
public class ImagenServicio {

    @Autowired
    ImagenRepositorio imagenRepositorio;

    // Guarda imagen del perfil del proveedor
    public Imagen guardarImagenProveedor(MultipartFile archivo) throws MiException, IOException {
        if (archivo != null) {
            Imagen imagen = new Imagen();
            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);
        }
        return null;
    }

    // modifica la imagen en el perfil del proveedor
    public Imagen modificarImagenProveedor(MultipartFile archivo, String idImagen) throws MiException, IOException {
        if (archivo != null) {
            Imagen imagen = new Imagen();
            if (idImagen != null) {
                Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                if (respuesta.isPresent()) {
                    imagen = respuesta.get();
                }
            }
            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);
        }
        return null;
    }

    /*
     * //Metodo para subir una foto
     * public Imagen guardarImagen(MultipartFile archivo) throws MiException,
     * IOException {
     * if (archivo != null) {
     * Imagen imagen = new Imagen();
     * imagen.setMime(archivo.getContentType());
     * imagen.setNombre(archivo.getName());
     * imagen.setContenido(archivo.getBytes());
     * return imagenRepositorio.save(imagen);
     * }
     * return null;
     * }
     * //Metodo para cambiar una foto por otra
     * public Imagen modificarImagen(MultipartFile archivo, String idImagen) throws
     * MiException, IOException {
     * 
     * if (archivo != null) {
     * Imagen imagen = new Imagen();
     * if (idImagen != null) {
     * Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
     * if (respuesta.isPresent()) {
     * imagen = respuesta.get();
     * }
     * }
     * imagen.setMime(archivo.getContentType());
     * imagen.setNombre(archivo.getName());
     * imagen.setContenido(archivo.getBytes());
     * return imagenRepositorio.save(imagen);
     * }
     * return null;
     * }
     */

}

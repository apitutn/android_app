package utn.com.ar.delivery_ui_mobile;

import java.io.Serializable;


public class ProductoComida implements Serializable {

    private Integer id;
    private String descripcion;
    private String nombre;
    private byte[] imagen;
    private String urlImagen;

    public ProductoComida(Integer id, String nombre, String descripcion, String URLimagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
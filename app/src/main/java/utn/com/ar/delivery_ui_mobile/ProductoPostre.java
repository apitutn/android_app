package utn.com.ar.delivery_ui_mobile;

import android.graphics.Bitmap;

import java.io.Serializable;


public class ProductoPostre implements Serializable {

    private Integer id;
    private String descripcion;
    private String nombre;
    private Bitmap imagen;
    private String urlImagen;

    public ProductoPostre(Integer id, String nombre, String descripcion, String URLimage, Bitmap bmp) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.urlImagen = URLimage;
        this.imagen = bmp;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

}
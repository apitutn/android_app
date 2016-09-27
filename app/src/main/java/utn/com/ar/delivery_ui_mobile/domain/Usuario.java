package utn.com.ar.delivery_ui_mobile.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private String clave;
    private String nombreUsuario;
    private String email;
    private Date fechaNac;
    private String nroTelefono;
    private TarjetaCredito tarjeta;

    public Usuario(String nombreUsuario, String nombre, String apellido, String clave, String cuil, String email, Date fechaNac, String nroTelefono) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.email = email;
        this.fechaNac = fechaNac;
        this.tarjeta = tarjeta;
        this.nroTelefono = nroTelefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("tarjeta")

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjeta = tarjetaCredito;
    }

    public String getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

}
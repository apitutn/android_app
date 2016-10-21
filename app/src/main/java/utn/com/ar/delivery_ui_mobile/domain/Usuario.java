package utn.com.ar.delivery_ui_mobile.domain;

public class Usuario {

    private String name;
    private String password;
    private String user;
    private String mail;

    public Usuario(String user, String name, String password, String mail) {
        this.user = user;
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getClave() {
        return password;
    }

    public void setClave(String clave) {
        this.password = clave;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
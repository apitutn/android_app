package utn.com.ar.delivery_ui_mobile.domain;

public class TarjetaCredito {
    private Long id;
    private String entidad;
    private String tokenMP;

    public TarjetaCredito(Long id, String entidad, String tokenMP) {
        this.id = id;
        this.entidad = entidad;
        this.tokenMP = tokenMP;
    }

    public TarjetaCredito() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idTarjeta) {
        this.id = idTarjeta;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getTokenMP() {
        return tokenMP;
    }

    public void setTokenMP(String tokenMP) {
        this.tokenMP = tokenMP;
    }


}

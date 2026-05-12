package pe.edu.upeu.msmelamine.dto;

import java.math.BigDecimal;

public class MelamineResponse {

    private Long id;
    private BigDecimal ancho;
    private BigDecimal largo;
    private String color;
    private String marca;

    private Long estadoId;
    private String estadoNombre;

    private String foto;

    public MelamineResponse() {
    }

    public MelamineResponse(Long id, BigDecimal ancho, BigDecimal largo, String color, String marca, Long estadoId, String estadoNombre, String foto) {
        this.id = id;
        this.ancho = ancho;
        this.largo = largo;
        this.color = color;
        this.marca = marca;
        this.estadoId = estadoId;
        this.estadoNombre = estadoNombre;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

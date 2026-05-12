package pe.edu.upeu.msmelamine.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MelamineRequest {

    @NotNull(message = "El ancho es obligatorio")
    private BigDecimal ancho;
    @NotNull(message = "El largo es obligatorio")
    private BigDecimal largo;
    private String color;
    private String marca;
    @NotNull(message = "El estado es obligatorio")
    private Long estadoId;
    private String foto;

    public MelamineRequest() {
    }

    public MelamineRequest(BigDecimal ancho, BigDecimal largo, String color, String marca, Long estadoId, String foto) {
        this.ancho = ancho;
        this.largo = largo;
        this.color = color;
        this.marca = marca;
        this.estadoId = estadoId;
        this.foto = foto;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

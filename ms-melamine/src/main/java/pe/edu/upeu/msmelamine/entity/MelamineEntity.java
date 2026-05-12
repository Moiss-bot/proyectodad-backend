package pe.edu.upeu.msmelamine.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "melamine")

public class MelamineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ANCHO", length = 100, nullable = false)
    private BigDecimal ancho;

    @Column(name = "LARGO", length = 100, nullable = false)
    private BigDecimal largo;

    @Column(name = "COLOR", length = 100, nullable = false)
    private String color;

    @Column(name = "MARCA", length = 100, nullable = false)
    private String marca;

    @Column(name = "ESTADO_ID", nullable = false)
    private Long estadoId;

    @Column(name = "FOTO", length = 500, nullable = false)
    private String foto;

    public MelamineEntity() {
    }

    public MelamineEntity(Long id, BigDecimal ancho, BigDecimal largo, String color, String marca, Long estadoId, String foto) {
        this.id = id;
        this.ancho = ancho;
        this.largo = largo;
        this.color = color;
        this.marca = marca;
        this.estadoId = estadoId;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}

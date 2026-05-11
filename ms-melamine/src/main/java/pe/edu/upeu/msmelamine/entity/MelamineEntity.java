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

}

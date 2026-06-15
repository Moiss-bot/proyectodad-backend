package pe.edu.upeu.msclientes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")

public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", length = 100, nullable = false)
    private String apellido;

    @Column(name = "DNI", length = 8)
    private String dni;

    @Column(name = "CORREO", length = 100, nullable = false)
    private String correo;

    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    @Column(name = "DIRECCION", length = 255)
    private String direccion;



    public ClienteEntity() {
    }

    public ClienteEntity(Long id, String nombre, String apellido, String dni, String correo, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni=dni;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
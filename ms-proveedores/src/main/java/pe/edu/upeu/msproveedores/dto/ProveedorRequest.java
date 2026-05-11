package pe.edu.upeu.msproveedores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProveedorRequest {

    @NotBlank(message = "Los nombres son obligatorio")
    @Size(max = 100, message = "Los nombres no debe exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos es obligatorio")
    @Size(max = 100, message = "Los apellidos no debe exceder 100 caracteres")
    private String apellidos;

    private Integer telefono;

    @NotNull(message = "La categoría es obligatoria")
    private long categoriaId;

    @NotBlank(message = "La dirección es obligatorio")
    @Size(max = 100, message = "La dirección no debe exceder 100 caracteres")
    private String direccion;

    @NotBlank(message = "La ubicación es obligatorio")
    @Size(max = 100, message = "La ubicación no debe exceder 100 caracteres")
    private String ubicacion;

    @NotBlank(message = "La foto es obligatorio")
    @Size(max = 500, message = "La foto no debe exceder 100 caracteres")
    private String foto;

    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 200, message = "La descripción no debe exceder 200 caracteres")
    private String descripcion;

    public ProveedorRequest() {
    }

    public ProveedorRequest(String nombres, String apellidos, Integer telefono, long categoriaId, String direccion, String ubicacion, String foto, String descripcion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.categoriaId = categoriaId;
        this.direccion = direccion;
        this.ubicacion = ubicacion;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

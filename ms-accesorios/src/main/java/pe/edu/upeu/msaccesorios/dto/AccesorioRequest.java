package pe.edu.upeu.msaccesorios.dto;

import jakarta.validation.constraints.*;

public class AccesorioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio — debe ser un número decimal")
    private Double precio;

    @NotNull(message = "El stock es obligatorio — debe ser un número entero")
    private Integer stock;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(min = 2, max = 50, message = "La categoría debe tener entre 2 y 50 caracteres")
    private String categoria;

    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;

    public AccesorioRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPrecioAsDouble() {
        if (precio == null) return null;
        // Ya no necesitas hacer Double.parseDouble() porque ya es un Double
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
        if (precio > 999.99) throw new IllegalArgumentException("El precio no puede exceder 999.99");
        return precio;
    }

    public Integer getStockAsInteger() {
        if (stock == null) return null;
        // Ya no necesitas hacer Integer.parseInt() porque ya es un Integer
        if (stock < 1) throw new IllegalArgumentException("El stock debe ser mayor a 0");
        if (stock > 1000) throw new IllegalArgumentException("El stock no puede exceder 1000 unidades");
        return stock;
    }
}
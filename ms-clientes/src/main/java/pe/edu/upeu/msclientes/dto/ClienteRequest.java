package pe.edu.upeu.msclientes.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe exceder 100 caracteres")
    private String apellido;

    @NotBlank(message = "El dni es obligatorio")
    @Size(max = 8, message = "El dni no debe exceder 8 caracteres")
    private String dni;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String correo;

    @Size(max = 9, message = "El teléfono no debe exceder 9 caracteres")
    private String telefono;

    @Size(max = 255, message = "La dirección no debe exceder 255 caracteres")
    private String direccion;

    public ClienteRequest() {
    }

    public ClienteRequest(String nombre, String apellido,String dni, String correo, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni=dni;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
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
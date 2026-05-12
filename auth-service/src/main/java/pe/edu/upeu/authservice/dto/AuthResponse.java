package pe.edu.upeu.authservice.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private String rol;

    public AuthResponse(String token, String username, String rol) {
        this.token = token;
        this.username = username;
        this.rol = rol;
    }
}
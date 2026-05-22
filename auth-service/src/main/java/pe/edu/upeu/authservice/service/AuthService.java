package pe.edu.upeu.authservice.service;

import Nlombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.authservice.dto.AuthRequest;
import pe.edu.upeu.authservice.dto.AuthResponse;
import pe.edu.upeu.authservice.entity.Usuario;
import pe.edu.upeu.authservice.repository.UsuarioRepository;
private final LoginService loginService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwTService jwtService;

    public AuthResponse login(AuthRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado. Verifique sus credenciales."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta. Verifique sus credenciales.");
        }

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token, usuario.getUsername(), usuario.getRol());
    }

    public AuthResponse register(AuthRequest request, String rol) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario '" + request.getUsername() + "' ya está registrado. Por favor, elija otro nombre de usuario.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token, usuario.getUsername(), usuario.getRol());
    }

    public AuthResponse loginConRol(AuthRequest request, String rolRequerido) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        if (!usuario.getRol().equals(rolRequerido)) {
            throw new RuntimeException("Acceso denegado. Este endpoint es exclusivo para " + rolRequerido + ". Su cuenta tiene el rol: " + usuario.getRol() + ".");
        }

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token, usuario.getUsername(), usuario.getRol());
    }
}
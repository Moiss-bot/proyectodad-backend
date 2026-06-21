package pe.edu.upeu.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.authservice.dto.AuthRequest;
import pe.edu.upeu.authservice.dto.AuthResponse;
import pe.edu.upeu.authservice.entity.Usuario;
import pe.edu.upeu.authservice.repository.UsuarioRepository;
import pe.edu.upeu.authservice.dto.UsuarioResponse;
import java.util.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwTService jwtService;
    private final LoginService loginService;

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
            throw new RuntimeException("El usuario '" + request.getUsername() + "' ya está registrado.");
        }
        if (request.getDni() != null && usuarioRepository.findByDni(request.getDni()).isPresent()) {
            throw new RuntimeException("El DNI '" + request.getDni() + "' ya está registrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setDni(request.getDni());
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token, usuario.getUsername(), usuario.getRol());
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(
                        u.getId(), u.getUsername(), u.getNombres(),
                        u.getApellidos(), u.getDni(), u.getRol()))
                .toList();
    }

    public AuthResponse loginConRol(AuthRequest request, String rolRequerido) {

        if (loginService.estaBloqueado(request.getUsername())) {
            LocalDateTime desbloqueo = loginService.getTiempoDesbloqueo(request.getUsername());
            String horaBloqueo = desbloqueo.minusMinutes(5).toLocalTime().toString().substring(0, 5);
            String horaDesbloqueo = desbloqueo.toLocalTime().toString().substring(0, 5);
            throw new RuntimeException(
                    " CUENTA BLOQUEADA TEMPORALMENTE | " +
                            "Bloqueada desde las: " + horaBloqueo + " | " +
                            "Se desbloqueará a las: " + horaDesbloqueo + " | " +
                            "Motivo: Múltiples intentos fallidos de inicio de sesión. " +
                            "Tiempo de espera: 5 minutos."
            );
        }

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElse(null);

        if (usuario == null) {
            loginService.registrarIntentoFallido(request.getUsername());
            int restantes = loginService.getIntentosRestantes(request.getUsername());
            throw new RuntimeException(
                    " Usuario no encontrado. " +
                            "Intentos fallidos registrados. " +
                            "Intentos restantes antes del bloqueo: " + restantes + "/3. " +
                            (restantes == 1 ? "¡ADVERTENCIA: Próximo intento fallido bloqueará su acceso por 5 minutos!" : "")
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            loginService.registrarIntentoFallido(request.getUsername());
            int restantes = loginService.getIntentosRestantes(request.getUsername());
            if (restantes <= 0) {
                LocalDateTime desbloqueo = loginService.getTiempoDesbloqueo(request.getUsername());
                String horaDesbloqueo = desbloqueo.toLocalTime().toString().substring(0, 5);
                throw new RuntimeException(
                        " ACCESO BLOQUEADO | " +
                                "Ha superado el máximo de 3 intentos fallidos. " +
                                "Su cuenta quedará bloqueada por 5 minutos. " +
                                "Podrá intentarlo nuevamente a las: " + horaDesbloqueo
                );
            }
            throw new RuntimeException(
                    " Contraseña incorrecta. " +
                            "Intentos restantes antes del bloqueo: " + restantes + "/3. " +
                            (restantes == 1 ? "¡ADVERTENCIA: Próximo intento fallido bloqueará su acceso por 5 minutos!" : "")
            );
        }

        if (!usuario.getRol().equals(rolRequerido)) {
            throw new RuntimeException(
                    " Acceso denegado. " +
                            "Este endpoint es exclusivo para el rol: " + rolRequerido + ". " +
                            "Su cuenta tiene el rol: " + usuario.getRol() + ". " +
                            "Por favor use el endpoint correcto."
            );
        }

        loginService.limpiarIntentos(request.getUsername());
        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token, usuario.getUsername(), usuario.getRol());
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
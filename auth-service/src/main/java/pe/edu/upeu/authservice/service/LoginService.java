package pe.edu.upeu.authservice.service;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginService {

    private static final int MAX_INTENTOS = 3;
    private static final int MINUTOS_BLOQUEO = 5;

    private final Map<String, Integer> intentos = new HashMap<>();
    private final Map<String, LocalDateTime> bloqueados = new HashMap<>();

    public boolean estaBloqueado(String username) {
        if (bloqueados.containsKey(username)) {
            LocalDateTime tiempoBloqueo = bloqueados.get(username);
            if (LocalDateTime.now().isBefore(tiempoBloqueo)) {
                return true;
            } else {
                bloqueados.remove(username);
                intentos.remove(username);
            }
        }
        return false;
    }

    public LocalDateTime getTiempoDesbloqueo(String username) {
        return bloqueados.get(username);
    }

    public void registrarIntentoFallido(String username) {
        int contador = intentos.getOrDefault(username, 0) + 1;
        intentos.put(username, contador);
        if (contador >= MAX_INTENTOS) {
            bloqueados.put(username, LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEO));
            intentos.remove(username);
        }
    }

    public void limpiarIntentos(String username) {
        intentos.remove(username);
        bloqueados.remove(username);
    }

    public int getIntentosRestantes(String username) {
        int usados = intentos.getOrDefault(username, 0);
        return MAX_INTENTOS - usados;
    }
}
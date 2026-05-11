package pe.edu.upeu.msherramientas.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class ServicioExternoClient {
    
    @CircuitBreaker(name = "servicioExternoCB", fallbackMethod = "fallbackObtenerDatos")
    public String obtenerDatosDeOtroMicroservicio(Long id) {
        System.out.println("Intentando conectar con el microservicio externo...");
        throw new RuntimeException("¡Error simulado! El otro microservicio está caído.");
    }

    public String fallbackObtenerDatos(Long id, Throwable excepcion) {

        return "El servicio externo no está disponible en este momento. Devolviendo datos de respaldo para el ID: " + id;
    }
}

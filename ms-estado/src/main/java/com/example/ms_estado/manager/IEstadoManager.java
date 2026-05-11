package com.example.ms_estado.manager;

public interface IEstadoManager {
    // Para validar el nombre del estado con un servicio externo (si fuera necesario)
    String validarNombreEstadoExterno(String nombre) throws Exception;
}

package pe.edu.upeu.msaccesorios.mappers;

import org.springframework.stereotype.Component;
import pe.edu.upeu.msaccesorios.dto.AccesorioRequest;
import pe.edu.upeu.msaccesorios.dto.AccesorioResponse;
import pe.edu.upeu.msaccesorios.entity.AccesorioEntity;
import pe.edu.upeu.msaccesorios.errors.ValidationException;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AccesorioMapper {

    public AccesorioEntity toEntity(AccesorioRequest request) {
        Map<String, String> errores = validarRangos(request);
        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }
        AccesorioEntity entity = new AccesorioEntity();
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecioAsDouble());
        entity.setStock(request.getStockAsInteger());
        entity.setCategoria(request.getCategoria());
        entity.setMarca(request.getMarca());
        return entity;
    }

    public AccesorioResponse toResponse(AccesorioEntity entity) {
        return new AccesorioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getCategoria(),
                entity.getMarca(),
                entity.getEstado()
        );
    }

    public void updateEntity(AccesorioEntity entity, AccesorioRequest request) {
        Map<String, String> errores = validarRangos(request);
        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecioAsDouble());
        entity.setStock(request.getStockAsInteger());
        entity.setCategoria(request.getCategoria());
        entity.setMarca(request.getMarca());
    }

    private Map<String, String> validarRangos(AccesorioRequest request) {
        Map<String, String> errores = new LinkedHashMap<>();

        // 🎯 Se eliminó el bloqueo de números en Nombre y Descripción para permitir valores como "Tornillo2"

        if (request.getCategoria() != null && request.getCategoria().matches(".*\\d.*")) {
            errores.put("categoria", "La categoría solo debe contener letras, no números");
        }

        if (request.getMarca() != null && request.getMarca().matches(".*\\d.*")) {
            errores.put("marca", "La marca solo debe contener letras, no números");
        }

        // 🎯 VALIDACIÓN DE PRECIO (Corregida para tipos Double nativos)
        if (request.getPrecio() != null) {
            double precio = request.getPrecio();
            if (precio <= 0) {
                errores.put("precio", "El precio debe ser mayor a 0");
            } else if (precio > 999.99) {
                errores.put("precio", "El precio no puede exceder 999.99");
            }
        }

        // 🎯 VALIDACIÓN DE STOCK (Corregida para tipos Integer nativos)
        if (request.getStock() != null) {
            int stock = request.getStock();
            if (stock < 1) {
                errores.put("stock", "El stock debe ser mayor a 0");
            } else if (stock > 1000) {
                errores.put("stock", "El stock no puede exceder 1000 unidades");
            }
        }

        return errores;
    }
}
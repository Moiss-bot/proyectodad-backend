package pe.edu.upeu.msproveedores.mapper;

import pe.edu.upeu.msproveedores.dto.ProveedorRequest;
import pe.edu.upeu.msproveedores.dto.ProveedorResponse;
import pe.edu.upeu.msproveedores.entity.ProveedorEntity;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorEntity toEntity(ProveedorRequest request) {
        ProveedorEntity entity = new ProveedorEntity();
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setTelefono(request.getTelefono());
        entity.setCategoriaId(request.getCategoriaId());
        entity.setDireccion(request.getDireccion());
        entity.setUbicacion(request.getUbicacion());
        entity.setFoto(request.getFoto());
        entity.setDescripcion(request.getDescripcion());
        return entity;
    }

    public ProveedorResponse toResponse(ProveedorEntity entity) {
        return new ProveedorResponse(
                entity.getId(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getTelefono(),
                entity.getCategoriaId(),
                null,
                entity.getDireccion(),
                entity.getUbicacion(),
                entity.getFoto(),
                entity.getDescripcion()
        );
    }

    public void updateEntity(ProveedorEntity entity, ProveedorRequest request) {
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setTelefono(request.getTelefono());
        entity.setCategoriaId(request.getCategoriaId());
        entity.setDireccion(request.getDireccion());
        entity.setUbicacion(request.getUbicacion());
        entity.setFoto(request.getFoto());
        entity.setDescripcion(request.getDescripcion());
    }

}

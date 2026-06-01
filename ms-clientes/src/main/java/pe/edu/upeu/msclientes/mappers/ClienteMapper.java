package pe.edu.upeu.msclientes.mappers;

import org.springframework.stereotype.Component;
import pe.edu.upeu.msclientes.dto.ClienteRequest;
import pe.edu.upeu.msclientes.dto.ClienteResponse;
import pe.edu.upeu.msclientes.entity.ClienteEntity;

@Component
public class ClienteMapper {

    public ClienteEntity toEntity(ClienteRequest request) {
        ClienteEntity entity = new ClienteEntity();
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setTelefono(request.getTelefono());
        entity.setDireccion(request.getDireccion());
        return entity;
    }

    public ClienteResponse toResponse(ClienteEntity entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getDireccion()
        );
    }

    public void updateEntity(ClienteEntity entity, ClienteRequest request) {
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setTelefono(request.getTelefono());
        entity.setDireccion(request.getDireccion());
    }
}
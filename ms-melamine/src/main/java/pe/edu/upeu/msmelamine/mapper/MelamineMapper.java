package pe.edu.upeu.msmelamine.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.msmelamine.dto.MelamineRequest;
import pe.edu.upeu.msmelamine.dto.MelamineResponse;
import pe.edu.upeu.msmelamine.entity.MelamineEntity;

@Component

public class MelamineMapper {

    public MelamineEntity toEntity(MelamineRequest request) {
        MelamineEntity entity = new MelamineEntity();
        entity.setAncho(request.getAncho());
        entity.setLargo(request.getLargo());
        entity.setColor(request.getColor());
        entity.setMarca(request.getMarca());
        entity.setEstadoId(request.getEstadoId());
        entity.setFoto(request.getFoto());
        return entity;
    }

    public MelamineResponse toResponse(MelamineEntity entity) {
        MelamineResponse res = new MelamineResponse();
        res.setId(entity.getId());
        res.setAncho(entity.getAncho());
        res.setLargo(entity.getLargo());
        res.setColor(entity.getColor());
        res.setMarca(entity.getMarca());
        res.setEstadoId(entity.getEstadoId());
        res.setEstadoNombre(null); // 👈 Se llenará en el Service
        res.setFoto(entity.getFoto());
        return res;
    }

    public void updateEntity(MelamineEntity entity, MelamineRequest request) {
        entity.setAncho(request.getAncho());
        entity.setLargo(request.getLargo());
        entity.setColor(request.getColor());
        entity.setMarca(request.getMarca());
        entity.setEstadoId(request.getEstadoId());
        entity.setFoto(request.getFoto());
    }

}

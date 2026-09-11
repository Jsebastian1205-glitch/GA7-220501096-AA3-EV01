package com.pccompare.gestionusuarios.util;

import com.pccompare.gestionusuarios.dto.response.SuscripcionResponse;
import com.pccompare.gestionusuarios.model.Suscripcion;

public final class SuscripcionMapper {

    private SuscripcionMapper() {
    }

    public static SuscripcionResponse aResponse(Suscripcion suscripcion) {
        if (suscripcion == null) {
            return null;
        }
        return SuscripcionResponse.builder()
                .id(suscripcion.getId())
                .usuarioId(suscripcion.getUsuario().getId())
                .plan(PlanSuscripcionMapper.aResponse(suscripcion.getPlan()))
                .fechaInicio(suscripcion.getFechaInicio())
                .fechaFin(suscripcion.getFechaFin())
                .estado(suscripcion.getEstado())
                .build();
    }
}

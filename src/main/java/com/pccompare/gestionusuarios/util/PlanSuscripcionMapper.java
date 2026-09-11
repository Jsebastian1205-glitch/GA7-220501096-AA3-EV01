package com.pccompare.gestionusuarios.util;

import com.pccompare.gestionusuarios.dto.response.PlanSuscripcionResponse;
import com.pccompare.gestionusuarios.model.PlanSuscripcion;

public final class PlanSuscripcionMapper {

    private PlanSuscripcionMapper() {
    }

    public static PlanSuscripcionResponse aResponse(PlanSuscripcion plan) {
        if (plan == null) {
            return null;
        }
        return PlanSuscripcionResponse.builder()
                .id(plan.getId())
                .nombre(plan.getNombre())
                .descripcion(plan.getDescripcion())
                .precio(plan.getPrecio())
                .duracionDias(plan.getDuracionDias())
                .activo(plan.isActivo())
                .build();
    }
}

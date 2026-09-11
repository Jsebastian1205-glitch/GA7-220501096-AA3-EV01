package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Historia de usuario: "Como usuario quiero suscribirme a un plan".
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class SuscribirseRequest {

    @NotNull(message = "Debe indicar el plan al que desea suscribirse")
    private Long planId;

    public SuscribirseRequest() {
    }

    public SuscribirseRequest(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}

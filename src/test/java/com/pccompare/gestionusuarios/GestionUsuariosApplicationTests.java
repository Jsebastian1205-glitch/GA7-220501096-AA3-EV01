package com.pccompare.gestionusuarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Prueba de humo: verifica que el contexto de Spring del módulo carga
 * correctamente (configuración, seguridad, JPA con H2 en el perfil "dev").
 */
@SpringBootTest
@ActiveProfiles("dev")
class GestionUsuariosApplicationTests {

    @Test
    void elContextoDeAplicacionCargaCorrectamente() {
        // Si el contexto de Spring no logra inicializarse, esta prueba falla.
    }
}

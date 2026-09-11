package com.pccompare.gestionusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del módulo de Gestión de Usuarios de la plataforma
 * PC Compare (comparador de componentes de PC).
 *
 * <p>Este módulo es responsable de:
 * <ul>
 *   <li>Registro y autenticación de usuarios (administradores y usuarios finales).</li>
 *   <li>Administración de perfiles y estados de cuenta.</li>
 *   <li>Gestión de planes de suscripción y de las suscripciones de cada usuario.</li>
 * </ul>
 *
 * <p>Puede ejecutarse de forma independiente (microservicio) o integrarse
 * como módulo dentro de la plataforma completa de PC Compare.
 */
@SpringBootApplication
public class GestionUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionUsuariosApplication.class, args);
    }
}

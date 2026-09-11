package com.pccompare.gestionusuarios.security;

import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Obtiene el {@link Usuario} correspondiente a la identidad autenticada en
 * la petición actual. Se usa en los controladores para resolver "el propio
 * usuario" en endpoints de tipo "mi perfil" / "mi suscripción", sin
 * necesidad de recibir el id explícitamente desde el cliente.
 */
@Component
@RequiredArgsConstructor
public class UsuarioAutenticadoProvider {

    private final UsuarioService usuarioService;

    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return usuarioService.buscarPorEmail(email);
    }
}

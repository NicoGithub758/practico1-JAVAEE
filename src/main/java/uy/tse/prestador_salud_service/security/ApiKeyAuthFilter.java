package uy.tse.prestador_salud_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private final String serverApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestApiKey = request.getHeader(API_KEY_HEADER);

        if (serverApiKey.equals(requestApiKey)) {
            // --- INICIO DE LA CORRECCIÓN ---
            // La clave es válida. Creamos un objeto de autenticación.
            // Le pasamos un "principal" (el sujeto, en este caso la propia API key),
            // "credentials" (null porque ya la validamos) y "authorities" (permisos, ninguno en este caso).
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    requestApiKey,
                    null,
                    Collections.emptyList() // No se necesitan roles/autoridades específicas
            );

            // Establecemos el objeto de autenticación en el contexto de seguridad.
            // ¡Esto le dice a Spring Security que la petición está autenticada!
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // --- FIN DE LA CORRECCIÓN ---

            // Permitir que la petición continúe.
            filterChain.doFilter(request, response);
        } else {
            // La clave es inválida o no está presente, rechazar la petición.
            // Limpiamos el contexto por si acaso.
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso no autorizado: API Key inválida o ausente.");
        }
    }
}

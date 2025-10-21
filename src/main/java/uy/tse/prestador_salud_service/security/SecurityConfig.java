package uy.tse.prestador_salud_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer; // Importa esta clase
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${prestador.api.secret-key}")
    private String apiKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Añadir nuestro filtro personalizado ANTES del filtro de autenticación estándar
            // ESTE FILTRO NO SE APLICARÁ A LAS RUTAS IGNORADAS POR webSecurityCustomizer()
            .addFilterBefore(new ApiKeyAuthFilter(apiKey), UsernamePasswordAuthenticationFilter.class)

            .authorizeHttpRequests(auth -> auth
                // Para cualquier otra petición (que no ha sido ignorada), se requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Permitir que la consola H2 se muestre en un frame. 
            // Esto sigue siendo necesario incluso si la ruta es ignorada,
            // ya que la configuración de cabeceras es independiente de la cadena de filtros.
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
            );

        return http.build();
    }

    // --- NUEVA CONFIGURACIÓN: Ignorar la consola H2 de la cadena de seguridad ---
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
    // --- FIN DE LA NUEVA CONFIGURACIÓN ---
}

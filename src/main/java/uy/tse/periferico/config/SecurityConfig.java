package uy.tse.periferico.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

import uy.tse.periferico.security.JwtTenantFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTenantFilter jwtTenantFilter;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Rutas públicas: cualquiera puede acceder a los logins y a la config
                .requestMatchers("/{tenantId}/api/auth/login/**", "/{tenantId}/api/config").permitAll()

                // Rutas de Administrador: solo quienes tengan la autoridad 'ROLE_ADMIN'
                .requestMatchers("/{tenantId}/api/admin/**").hasAuthority("ROLE_ADMIN")
                
                // Rutas de Documentos: quienes tengan 'ROLE_PROFESIONAL' O 'ROLE_SYSTEM'
                .requestMatchers("/{tenantId}/api/documentos/**").hasAnyAuthority("ROLE_PROFESIONAL", "ROLE_SYSTEM")

                // Para cualquier otra ruta que no coincida, solo se requiere estar autenticado
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtTenantFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Permitir peticiones desde tu frontend de React
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    // Permitir los métodos HTTP que usará tu frontend
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // Permitir las cabeceras que enviará tu frontend
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Aplicar esta configuración a TODAS las rutas de tu API
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
}


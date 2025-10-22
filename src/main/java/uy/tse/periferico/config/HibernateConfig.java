package uy.tse.periferico.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HibernateConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        return properties -> properties.putAll(Map.of(
                // Usamos la cadena de texto directamente para máxima compatibilidad
                "hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider,
                
                // Usamos la cadena de texto directamente para máxima compatibilidad
                "hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver
        ));
    }
}
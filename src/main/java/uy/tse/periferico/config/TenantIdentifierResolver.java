package uy.tse.periferico.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        // Si no hay tenant en el contexto, se usa el schema por defecto (usualmente 'public')
        return tenantId != null ? tenantId : "public";
    }
    @Override
    public boolean validateExistingCurrentSessions() { return true; }
}

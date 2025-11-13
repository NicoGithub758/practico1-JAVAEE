package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.tse.periferico.config.TenantContext;
import uy.tse.periferico.dto.TenantConfigUpdateDTO;
import uy.tse.periferico.model.TenantConfiguracion;
import uy.tse.periferico.service.TenantConfiguracionService;

@RestController
@RequestMapping("/{tenantId}/api/config")
@RequiredArgsConstructor
public class TenantConfiguracionController {

    private final TenantConfiguracionService configService;

    @GetMapping
    public ResponseEntity<TenantConfiguracion> getTenantConfig(@PathVariable String tenantId) {
        // IMPORTANTE: Establecemos el contexto del tenant ANTES de llamar al servicio
        TenantContext.setCurrentTenant(tenantId);
        try {
            TenantConfiguracion config = configService.getConfiguration();
            return ResponseEntity.ok(config);
        } finally {
            // Y nos aseguramos de limpiarlo después
            TenantContext.clear();
        }
    }

    @PutMapping("/admin/config")
    public ResponseEntity<TenantConfiguracion> updateTenantConfig(
            @PathVariable String tenantId,
            @RequestBody TenantConfigUpdateDTO updateDTO) { // Recibe el DTO desde el frontend

        TenantContext.setCurrentTenant(tenantId);
        try {
            TenantConfiguracion updatedConfig = configService.updateConfiguration(updateDTO);
            return ResponseEntity.ok(updatedConfig);
        } finally {
            TenantContext.clear();
        }
    }
}
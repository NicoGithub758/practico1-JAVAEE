package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.tse.periferico.dto.SolicitudAccesoRequestDTO;
import uy.tse.periferico.service.HcenAccesoService;

@RestController
@RequestMapping("/{tenantId}/api/accesos")
@RequiredArgsConstructor
public class AccesoController {

    private final HcenAccesoService accesoService;

    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarAcceso(@PathVariable String tenantId, @RequestBody SolicitudAccesoRequestDTO dto) {
        // Aseguramos que el tenant que solicita es el correcto
        dto.setSchemaTenantSolicitante(tenantId);
        try {
            String respuesta = accesoService.solicitarAcceso(dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

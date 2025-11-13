package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.tse.periferico.dto.DocumentoDetalleDTO;
import uy.tse.periferico.service.HcenDocumentoExternoService;

@RestController
@RequestMapping("/{tenantId}/api/documento-externo")
@RequiredArgsConstructor
public class DocumentoExternoController {

    private final HcenDocumentoExternoService documentoExternoService;

    @GetMapping
    public ResponseEntity<DocumentoDetalleDTO> getDocumento(
            @PathVariable String tenantId,
            @RequestParam String cedulaPaciente,
            @RequestParam String docId) {
        
        DocumentoDetalleDTO documento = documentoExternoService.obtenerDocumento(tenantId, cedulaPaciente, docId);
        return ResponseEntity.ok(documento);
    }
}
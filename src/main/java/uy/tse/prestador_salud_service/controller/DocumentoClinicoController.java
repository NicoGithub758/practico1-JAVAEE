package uy.tse.prestador_salud_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.tse.prestador_salud_service.dto.DocumentoClinicoDTO;
import uy.tse.prestador_salud_service.service.DocumentoClinicoService;



@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoClinicoController {

    private final DocumentoClinicoService documentoClinicoService;

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoClinicoDTO> getDocumentoClinicoById(@PathVariable Long id) {
        // La excepción ResourceNotFoundException se manejará globalmente gracias a @ResponseStatus
        DocumentoClinicoDTO documento = documentoClinicoService.findDocumentoById(id);
        return ResponseEntity.ok(documento);
    }
}
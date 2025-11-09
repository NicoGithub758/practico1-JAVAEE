package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uy.tse.periferico.model.Paciente;
import uy.tse.periferico.service.PacienteService;

import java.util.List;

@RestController
@RequestMapping("/{tenantId}/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/search")
    public ResponseEntity<List<Paciente>> searchPacientesByNroDocumento(@RequestParam("nroDocumento") String nroDocumento) {
        return ResponseEntity.ok(pacienteService.findByNroDocumento(nroDocumento));
    }
}
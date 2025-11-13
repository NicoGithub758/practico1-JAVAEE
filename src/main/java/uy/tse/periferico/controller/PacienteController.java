package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uy.tse.periferico.dto.PacienteCreateDTO;
import uy.tse.periferico.model.Paciente;
import uy.tse.periferico.service.PacienteService;

import org.springframework.web.bind.annotation.*; // AÑADIR IMPORT
import uy.tse.periferico.dto.SolicitudAccesoDTO; // AÑADIR IMPORT
import uy.tse.periferico.model.Paciente;


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

    @PostMapping("/solicitar-acceso")
    public ResponseEntity<String> solicitarAccesoPaciente(
            @AuthenticationPrincipal String cedulaProfesional, // Obtenido del token JWT
            @RequestBody SolicitudAccesoDTO solicitud) {
        try {
            String mensaje = pacienteService.solicitarAcceso(cedulaProfesional, solicitud.getCedulaPaciente());
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            // Devolvemos el mensaje de error del servicio con un estado 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/{tenantId}/api/admin/pacientes")
    public ResponseEntity<Paciente> crearPacienteLocal(@RequestBody PacienteCreateDTO createDTO) {
        Paciente pacienteCreado = pacienteService.crearPacienteLocal(createDTO);
        return new ResponseEntity<>(pacienteCreado, HttpStatus.CREATED);
    }

}
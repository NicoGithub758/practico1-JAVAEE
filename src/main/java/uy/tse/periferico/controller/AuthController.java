package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.tse.periferico.dto.LoginRequest;
import uy.tse.periferico.dto.LoginResponse;
import uy.tse.periferico.service.AutenticacionService;
import org.springframework.web.bind.annotation.PathVariable;
import uy.tse.periferico.service.ProfesionalService;
import uy.tse.periferico.dto.ProfesionalDTO;
import org.springframework.web.bind.annotation.PutMapping;
import uy.tse.periferico.dto.ProfesionalProfileUpdateDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
@RequestMapping("/{tenantId}/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AutenticacionService autenticacionService;
    private final ProfesionalService profesionalService;

    // Renombrado para mayor claridad (de /login a /login/profesional)
    @PostMapping("/login/profesional")
    public ResponseEntity<LoginResponse> loginProfesional(@PathVariable String tenantId, @RequestBody LoginRequest loginRequest) {
        String token = autenticacionService.loginProfesional(loginRequest, tenantId);
        return ResponseEntity.ok(new LoginResponse(token));
    }
    
    // --- NUEVO ENDPOINT PARA EL LOGIN DEL ADMINISTRADOR ---
    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponse> loginAdmin(@PathVariable String tenantId, @RequestBody LoginRequest loginRequest) {
        // Llama a la nueva función del servicio
        String token = autenticacionService.loginAdmin(loginRequest, tenantId);
        // Devuelve el token en la respuesta
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PutMapping("/profesionales/perfil")
    public ResponseEntity<ProfesionalDTO> updateOwnProfile(@AuthenticationPrincipal String username, @RequestBody ProfesionalProfileUpdateDTO updateDTO) {
        ProfesionalDTO profesionalActualizado = profesionalService.updateOwnProfile(username, updateDTO);
        return ResponseEntity.ok(profesionalActualizado);
    }
}


package uy.tse.periferico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.tse.periferico.config.TenantContext; // <-- AÑADE ESTA IMPORTACIÓN
import uy.tse.periferico.dto.LoginRequest;
import uy.tse.periferico.dto.LoginResponse;
import uy.tse.periferico.service.AutenticacionService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. Establecer el contexto del tenant ANTES de llamar al servicio
         System.out.println("DEBUG: AuthController -> Tenant ID recibido en el request: [" + loginRequest.getTenantId() + "]");
        TenantContext.setCurrentTenant(loginRequest.getTenantId());

        try {
            // 2. Llamar al servicio, que ahora operará dentro del contexto correcto
            String token = autenticacionService.login(loginRequest);
            return ResponseEntity.ok(new LoginResponse(token));
        } finally {
            // 3. MUY IMPORTANTE: Limpiar el contexto después de que la petición termine
            TenantContext.clear();
        }
    }
}


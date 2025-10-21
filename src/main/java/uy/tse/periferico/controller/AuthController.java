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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = autenticacionService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}


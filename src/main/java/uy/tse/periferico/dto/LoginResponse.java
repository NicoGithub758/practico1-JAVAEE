package uy.tse.periferico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Crea un constructor con todos los campos.
public class LoginResponse {
// El token JWT que el frontend deberá guardar y usar en peticiones futuras.
private String token;
}

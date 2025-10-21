package uy.tse.periferico.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class LoginRequest {
    private String tenantId;
    private String username;
    private String password;
}


package uy.tse.prestador_salud_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profesionalId;
    
    @Column(unique = true)
    private String nombreUsuario;
    
    private String nombre;
    private String apellido;
    private String especializacion;
}

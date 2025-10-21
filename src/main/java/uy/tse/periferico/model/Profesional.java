package uy.tse.periferico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profesionales", uniqueConstraints = {
    // Definimos una restricción de unicidad a nivel de tabla.
    // Esto asegura que la combinación de `username` y `tenant_id` (implícito) sea única.
    // Hibernate no usa esto directamente para DDL, pero es una buena práctica declararlo.
    // La unicidad real se garantizará a nivel de base de datos en el schema de cada tenant.
    @UniqueConstraint(columnNames = {"username"})
})
@Data
@NoArgsConstructor
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Usamos un 'Long' autoincremental. PostgreSQL lo manejará como un tipo 'bigserial'.
    // Esto es simple, rápido y funciona en todas partes.
    private Long id;

    @Column(nullable = false, unique = true)
    // Como cada tenant tiene su propio schema, la restricción 'unique = true'
    // se aplicará DENTRO de ese schema, lo cual es correcto.
    // 'jperez' será único en 'clinica_a.profesionales' y también en 'laboratorio_b.profesionales'.
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String especializacion;
}

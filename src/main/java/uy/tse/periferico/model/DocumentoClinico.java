package uy.tse.periferico.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "documento_clinico") 
@Data
@NoArgsConstructor
public class DocumentoClinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id") 
    private Long docId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id", nullable = false)
    private Profesional profesional;

    @Column(name = "id_externa_doc", unique = true) 
    private String idExternaDoc;

    private String tipo;
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String contenido; 

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
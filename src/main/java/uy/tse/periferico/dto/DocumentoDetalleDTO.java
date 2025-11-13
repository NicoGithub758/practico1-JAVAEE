package uy.tse.periferico.dto;

import lombok.Data;

@Data
public class DocumentoDetalleDTO {
    private String pacienteNombre;
    private String pacienteNroDocumento;
    private String instanciaMedica;
    private String fechaAtencion;
    private String autor;
    private String custodio;
    // Añade más campos según lo que quieras mostrar
}
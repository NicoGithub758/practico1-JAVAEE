package uy.tse.prestador_salud_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uy.tse.prestador_salud_service.dto.DocumentoClinicoDTO;
import uy.tse.prestador_salud_service.exception.ResourceNotFoundException;
import uy.tse.prestador_salud_service.model.DocumentoClinico;
import jakarta.transaction.Transactional;
import uy.tse.prestador_salud_service.repository.DocumentoClinicoRepository;



@Service
@RequiredArgsConstructor
public class DocumentoClinicoService {

    private final DocumentoClinicoRepository repository;
    
    @Transactional 
    public DocumentoClinicoDTO findDocumentoById(Long id) {
        DocumentoClinico doc = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento clínico no encontrado con id: " + id));
        
        return mapToDTO(doc);
    }

    private DocumentoClinicoDTO mapToDTO(DocumentoClinico doc) {
        DocumentoClinicoDTO dto = new DocumentoClinicoDTO();
        dto.setDocId(doc.getDocId());
        dto.setTipo(doc.getTipo());
        dto.setContenido(doc.getContenido());
        dto.setFechaCreacion(doc.getFechaCreacion());

        // Mapeo de Paciente
        DocumentoClinicoDTO.PacienteInfo pacienteInfo = new DocumentoClinicoDTO.PacienteInfo();
        pacienteInfo.setPacienteId(doc.getPaciente().getPacienteId());
        pacienteInfo.setNombreCompleto(doc.getPaciente().getNombre() + " " + doc.getPaciente().getApellido());
        dto.setPaciente(pacienteInfo);

        // Mapeo de Profesional
        DocumentoClinicoDTO.ProfesionalInfo profesionalInfo = new DocumentoClinicoDTO.ProfesionalInfo();
        profesionalInfo.setProfesionalId(doc.getProfesional().getProfesionalId());
        profesionalInfo.setNombreCompleto(doc.getProfesional().getNombre() + " " + doc.getProfesional().getApellido());
        profesionalInfo.setEspecializacion(doc.getProfesional().getEspecializacion());
        dto.setProfesional(profesionalInfo);

        return dto;
    }
}
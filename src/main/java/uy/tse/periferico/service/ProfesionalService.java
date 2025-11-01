// RUTA: src/main/java/uy/tse/periferico/service/ProfesionalService.java
package uy.tse.periferico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.tse.periferico.dto.ProfesionalCreateDTO;
import uy.tse.periferico.dto.ProfesionalDTO;
import uy.tse.periferico.dto.ProfesionalUpdateDTO;
import uy.tse.periferico.exception.ResourceAlreadyExistsException;
import uy.tse.periferico.exception.ResourceNotFoundException;
import uy.tse.periferico.model.Profesional;
import uy.tse.periferico.repository.ProfesionalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;
    private final PasswordEncoder passwordEncoder;

    // --- ALTA (CREATE) ---
    @Transactional
    public ProfesionalDTO createProfesional(ProfesionalCreateDTO createDTO) {
        // El TenantContext ya fue establecido por el filtro JWT
        profesionalRepository.findByUsername(createDTO.getUsername()).ifPresent(p -> {
            throw new ResourceAlreadyExistsException("El username '" + createDTO.getUsername() + "' ya existe.");
        });

        Profesional nuevoProfesional = new Profesional();
        nuevoProfesional.setUsername(createDTO.getUsername());
        nuevoProfesional.setNombre(createDTO.getNombre());
        nuevoProfesional.setApellido(createDTO.getApellido());
        nuevoProfesional.setEspecializacion(createDTO.getEspecializacion());

        // Hashear la contraseña antes de guardarla
        nuevoProfesional.setPasswordHash(passwordEncoder.encode(createDTO.getPassword()));

        Profesional profesionalGuardado = profesionalRepository.save(nuevoProfesional);
        return mapToDTO(profesionalGuardado);
    }

    // --- MODIFICACIÓN (UPDATE) ---
    @Transactional
    public ProfesionalDTO updateProfesional(Long id, ProfesionalUpdateDTO updateDTO) {
        Profesional profesional = profesionalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con id: " + id));

        profesional.setNombre(updateDTO.getNombre());
        profesional.setApellido(updateDTO.getApellido());
        profesional.setEspecializacion(updateDTO.getEspecializacion());

        // Si se provee una nueva contraseña, se actualiza
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            profesional.setPasswordHash(passwordEncoder.encode(updateDTO.getPassword()));
        }

        Profesional profesionalActualizado = profesionalRepository.save(profesional);
        return mapToDTO(profesionalActualizado);
    }

    // --- BAJA (DELETE) ---
    @Transactional
    public void deleteProfesional(Long id) {
        if (!profesionalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profesional no encontrado con id: " + id);
        }
        profesionalRepository.deleteById(id);
    }

    // --- CONSULTAS (READ) ---
    @Transactional(readOnly = true)
    public ProfesionalDTO findProfesionalById(Long id) {
        Profesional profesional = profesionalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con id: " + id));
        return mapToDTO(profesional);
    }

    @Transactional(readOnly = true)
    public List<ProfesionalDTO> findAllProfesionales() {
        return profesionalRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    // --- Mapeador privado ---
    private ProfesionalDTO mapToDTO(Profesional profesional) {
        ProfesionalDTO dto = new ProfesionalDTO();
        dto.setId(profesional.getId());
        dto.setUsername(profesional.getUsername());
        dto.setNombre(profesional.getNombre());
        dto.setApellido(profesional.getApellido());
        dto.setEspecializacion(profesional.getEspecializacion());
        return dto;
    }
}
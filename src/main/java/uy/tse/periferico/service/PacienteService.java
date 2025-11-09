package uy.tse.periferico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.tse.periferico.model.Paciente;
import uy.tse.periferico.repository.PacienteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<Paciente> findByNroDocumento(String nroDocumento) {
        // Devuelve una lista de pacientes cuyo número de documento contenga el string buscado
        return pacienteRepository.findByNroDocumentoContainingIgnoreCase(nroDocumento);
    }
}
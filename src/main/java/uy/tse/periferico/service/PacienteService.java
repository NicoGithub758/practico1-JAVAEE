package uy.tse.periferico.service;

// --- IMPORTS AÑADIDOS Y CORREGIDOS ---
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders; // ¡CORREGIDO! Usamos el de Spring
import org.springframework.http.MediaType;   // AÑADIDO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate; // AÑADIDO
import uy.tse.periferico.model.Paciente;
import uy.tse.periferico.repository.PacienteRepository;

import java.util.List;
import java.util.Map;

@Service
// ELIMINAMOS @RequiredArgsConstructor para usar un constructor manual
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    // --- DEPENDENCIAS AÑADIDAS ---
    private final RestTemplate restTemplate;
    @Value("${central.api.url.accesos}") // Asume que esta propiedad está en application.properties
    private String centralApiUrl;


    // --- CONSTRUCTOR MANUAL PARA INICIALIZAR TODO ---
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
        this.restTemplate = new RestTemplate(); // Inicializamos RestTemplate
    }

    @Transactional(readOnly = true)
    public List<Paciente> findByNroDocumento(String nroDocumento) {
        // Devuelve una lista de pacientes cuyo número de documento contenga el string buscado
        return pacienteRepository.findByNroDocumentoContainingIgnoreCase(nroDocumento);
    }

    // --- MÉTODO CORREGIDO ---
    public String solicitarAcceso(String cedulaProfesional, String cedulaPaciente) {
        try {
            // Preparamos el cuerpo de la petición para el sistema central
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Ahora funciona con el import correcto

            // El API central esperará un JSON con las dos cédulas
            Map<String, String> requestBody = Map.of(
                    "cedulaProfesional", cedulaProfesional,
                    "cedulaPaciente", cedulaPaciente
            );

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            // Hacemos la llamada POST al sistema central y esperamos una respuesta simple (String)
            String respuesta = restTemplate.postForObject(centralApiUrl, request, String.class);

            if (respuesta == null) {
                throw new RuntimeException("La respuesta del sistema central fue inválida.");
            }

            return respuesta; // Ej: "Solicitud de acceso enviada correctamente."

        } catch (HttpClientErrorException e) {
            // Capturamos errores específicos (ej. 404 si el paciente no existe, 403 si ya tiene acceso, etc.)
            // El mensaje de error vendrá del sistema central.
            throw new RuntimeException("Error desde el sistema central: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Error genérico de comunicación
            throw new RuntimeException("Error de comunicación con el sistema central: " + e.getMessage());
        }
    }
}
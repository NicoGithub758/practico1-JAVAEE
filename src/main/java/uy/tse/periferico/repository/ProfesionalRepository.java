package uy.tse.periferico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.tse.periferico.model.Profesional;
import java.util.Optional;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
    // Esta consulta es GENÉRICA. Hibernate la ejecutará en el schema correcto.
    Optional<Profesional> findByUsername(String username);
}

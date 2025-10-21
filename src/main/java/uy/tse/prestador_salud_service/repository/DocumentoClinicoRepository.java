package uy.tse.prestador_salud_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.tse.prestador_salud_service.model.DocumentoClinico;


@Repository
public interface DocumentoClinicoRepository extends JpaRepository<DocumentoClinico, Long> {
}

package com.corhuila.scotiabank.api_restful_backend.repository;

import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, Long> {
    Optional<ProgramaAcademico> findByNombre(String nombre);
}

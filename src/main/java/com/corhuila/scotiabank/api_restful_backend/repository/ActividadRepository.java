package com.corhuila.scotiabank.api_restful_backend.repository;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}

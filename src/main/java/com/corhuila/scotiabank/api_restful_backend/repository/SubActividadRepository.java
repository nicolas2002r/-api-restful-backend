package com.corhuila.scotiabank.api_restful_backend.repository;

import com.corhuila.scotiabank.api_restful_backend.entity.SubActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubActividadRepository extends JpaRepository<SubActividad, Long> {
    List<SubActividad> findByActividadId(Long actividadId);
}

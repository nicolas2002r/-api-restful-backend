package com.corhuila.scotiabank.api_restful_backend.repository;

import com.corhuila.scotiabank.api_restful_backend.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}

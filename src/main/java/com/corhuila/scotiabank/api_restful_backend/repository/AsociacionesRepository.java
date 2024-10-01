package com.corhuila.scotiabank.api_restful_backend.repository;

import com.corhuila.scotiabank.api_restful_backend.entity.Asociaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsociacionesRepository extends JpaRepository<Asociaciones, Long> {
}

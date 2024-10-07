package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.repository.ActividadRepository;
import com.corhuila.scotiabank.api_restful_backend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;

    @Override
    public List<Actividad> obtenerTodasLasActividades() {
        return actividadRepository.findAll();
    }
    public List<Actividad> obtenerActividadesPorUsuario(Long usuarioId) {
        return actividadRepository.findByUsuarioId(usuarioId);
    }
}

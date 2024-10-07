package com.corhuila.scotiabank.api_restful_backend.service;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;

import java.util.List;

public interface ActividadService {
    List<Actividad> obtenerTodasLasActividades();
    List<Actividad> obtenerActividadesPorUsuario(Long usuarioId);
}

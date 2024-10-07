package com.corhuila.scotiabank.api_restful_backend.controller;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    @GetMapping
    public ResponseEntity<List<Actividad>> listarActividades() {
        return ResponseEntity.ok(actividadService.obtenerTodasLasActividades());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Actividad>> listarActividadesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(actividadService.obtenerActividadesPorUsuario(usuarioId));
    }
}

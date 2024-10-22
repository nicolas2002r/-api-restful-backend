package com.corhuila.scotiabank.api_restful_backend.controller;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Actividad> crearActividad(@RequestBody ActividadDTO actividadDTO) {
        Actividad nuevaActividad = actividadService.crearActividad(actividadDTO);
        return new ResponseEntity<>(nuevaActividad, HttpStatus.CREATED);
    }
}

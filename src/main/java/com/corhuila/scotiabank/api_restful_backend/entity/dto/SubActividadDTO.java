package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubActividadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<TareaDTO> tareas;
}

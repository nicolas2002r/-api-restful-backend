package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TareaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String subActividad;
    private boolean asignable;
}

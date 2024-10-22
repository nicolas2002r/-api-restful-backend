package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ActividadDTO {
    private Long id;
    private String nombre;
    private String tipoActividad;
    private String descripcion;
    private BigDecimal dedicacionSemanal;
    private BigDecimal dedicacionSemestral;
    private UsuarioDTO usuario;
    private List<SubActividadDTO> subActividades;
}

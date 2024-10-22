package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AsociacionDTO {
    private Long id;
    private String rol;
    private String actividad;
}

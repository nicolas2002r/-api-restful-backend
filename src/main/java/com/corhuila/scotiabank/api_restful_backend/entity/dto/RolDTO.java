package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolDTO {
    private Long id;
    private String nombre;
    private List<UsuarioDTO> usuarios;
    private List<AsociacionDTO> asociaciones;
}

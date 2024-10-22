package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private RolDTO rol;
    private List<ProgramaAcademicoDTO> programasAcademicos;
    private List<String> actividades;
}

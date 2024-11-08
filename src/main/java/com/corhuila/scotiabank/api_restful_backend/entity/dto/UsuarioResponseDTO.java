package com.corhuila.scotiabank.api_restful_backend.entity.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private RolDTO rol; // Mantenemos el RolDTO si es necesario
    private List<ProgramaAcademicoDTO> programasAcademicos; // Listado de programas académicos

    @Getter
    @Setter
    public static class RolDTO {
        private Long id;
        private String nombre;
        // Eliminar usuarios y asociaciones si no son necesarios
    }

    @Getter
    @Setter
    public static class ProgramaAcademicoDTO {
        private Long id;
        private String nombre;
    }
}

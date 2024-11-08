package com.corhuila.scotiabank.api_restful_backend.controller;

import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ProgramaAcademicoDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioResponseDTO;
import com.corhuila.scotiabank.api_restful_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setApellido(usuarioDTO.getApellido());
            usuario.setDni(usuarioDTO.getDni());
            usuario.setCorreo(usuarioDTO.getCorreo());


            Usuario usuarioCreado = usuarioService.crearUsuario(
                    usuario,
                    usuarioDTO.getRol().getNombre(),
                    usuarioDTO.getProgramasAcademicos().stream()
                            .map(ProgramaAcademicoDTO::getNombre)
                            .collect(Collectors.toList())
            );

            return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Manejo de excepciones
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            List<UsuarioResponseDTO> usuariosResponse = usuarios.stream().map(usuario -> {
                UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
                responseDTO.setId(usuario.getId());
                responseDTO.setNombre(usuario.getNombre());
                responseDTO.setApellido(usuario.getApellido());
                responseDTO.setDni(usuario.getDni());
                responseDTO.setCorreo(usuario.getCorreo());

                // Crear y asignar RolDTO
                UsuarioResponseDTO.RolDTO rolDTO = new UsuarioResponseDTO.RolDTO();
                rolDTO.setId(usuario.getRol().getId());
                rolDTO.setNombre(usuario.getRol().getNombre());
                responseDTO.setRol(rolDTO);

                // Mapear programas académicos
                List<UsuarioResponseDTO.ProgramaAcademicoDTO> programas = usuario.getProgramasAcademicos().stream()
                        .map(programa -> {
                            UsuarioResponseDTO.ProgramaAcademicoDTO programaDTO = new UsuarioResponseDTO.ProgramaAcademicoDTO();
                            programaDTO.setId(programa.getId());
                            programaDTO.setNombre(programa.getNombre());
                            return programaDTO;
                        }).collect(Collectors.toList());
                responseDTO.setProgramasAcademicos(programas);

                return responseDTO;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(usuariosResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de excepciones
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioResponseDTO usuarioResponseDTO) {
        try {
            // Llamar al servicio para actualizar el usuario
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioResponseDTO);

            // Convertir a DTO de respuesta
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
            responseDTO.setNombre(usuarioActualizado.getNombre());
            responseDTO.setApellido(usuarioActualizado.getApellido());
            responseDTO.setDni(usuarioActualizado.getDni());
            responseDTO.setCorreo(usuarioActualizado.getCorreo());

            // Crear y asignar UsuarioResponseDTO.RolDTO
            UsuarioResponseDTO.RolDTO rolDTO = new UsuarioResponseDTO.RolDTO();
            rolDTO.setId(usuarioActualizado.getRol().getId());
            rolDTO.setNombre(usuarioActualizado.getRol().getNombre());
            responseDTO.setRol(rolDTO);

            // Mapear programas académicos
            responseDTO.setProgramasAcademicos(usuarioActualizado.getProgramasAcademicos().stream()
                    .map(programa -> {
                        UsuarioResponseDTO.ProgramaAcademicoDTO programaDTO = new UsuarioResponseDTO.ProgramaAcademicoDTO();
                        programaDTO.setId(programa.getId());
                        programaDTO.setNombre(programa.getNombre());
                        return programaDTO;
                    })
                    .collect(Collectors.toList()));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Manejo de excepciones (por ejemplo, usuario no encontrado)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejo de excepciones generales
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
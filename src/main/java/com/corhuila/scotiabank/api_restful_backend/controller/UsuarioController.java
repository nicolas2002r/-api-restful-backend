package com.corhuila.scotiabank.api_restful_backend.controller;

import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setCorreo(usuarioDTO.getCorreo());

        Usuario usuarioCreado = usuarioService.crearUsuario(
                usuario,
                usuarioDTO.getRolNombre(),
                usuarioDTO.getProgramasAcademicos()
        );

        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }
}

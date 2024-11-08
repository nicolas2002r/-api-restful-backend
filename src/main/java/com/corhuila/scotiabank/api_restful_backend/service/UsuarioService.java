package com.corhuila.scotiabank.api_restful_backend.service;

import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario, String nombreRol, List<String> nombresProgramas);

    List<Usuario> obtenerTodosLosUsuarios();

    // Método para actualizar un usuario
    Usuario actualizarUsuario(Long id, UsuarioResponseDTO usuarioResponseDTO);

    void eliminarUsuario(Long id);

}
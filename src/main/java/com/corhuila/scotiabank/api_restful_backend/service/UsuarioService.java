package com.corhuila.scotiabank.api_restful_backend.service;

import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario, String nombreRol, List<String> nombresProgramas);
}

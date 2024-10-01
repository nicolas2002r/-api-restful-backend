package com.corhuila.scotiabank.api_restful_backend.service;

import com.corhuila.scotiabank.api_restful_backend.entity.Rol;

import java.util.List;

public interface RolService {
    List<Rol> obtenerTodosLosRoles();
}

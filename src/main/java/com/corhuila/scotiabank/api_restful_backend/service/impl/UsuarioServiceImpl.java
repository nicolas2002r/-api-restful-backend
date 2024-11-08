package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.repository.ProgramaAcademicoRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.UsuarioRepository;
import com.corhuila.scotiabank.api_restful_backend.service.UsuarioService;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RolRepository rolRepository;

    private final ProgramaAcademicoRepository programaAcademicoRepository;

    /**
     * Metodo para crear usuario y asignar rol
     * @param usuario
     * @param nombreRol
     * @param nombresProgramas
     * @return
     */
    @Override
    public final Usuario crearUsuario(Usuario usuario, String nombreRol, List<String> nombresProgramas) {
        // Buscar y asignar el rol
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        // Buscar y asignar los programas académicos
        Set<ProgramaAcademico> programas = nombresProgramas.stream()
                .map(nombre -> programaAcademicoRepository.findByNombre(nombre)
                        .orElseThrow(() -> new RuntimeException("Programa académico no encontrado: " + nombre)))
                .collect(Collectors.toSet());
        usuario.setProgramasAcademicos(programas);

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }
    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll(); // Método que obtiene todos los usuarios
    }

    @Override
    public Usuario actualizarUsuario(Long id, UsuarioResponseDTO usuarioResponseDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuarioExistente.setNombre(usuarioResponseDTO.getNombre());
        usuarioExistente.setApellido(usuarioResponseDTO.getApellido());
        usuarioExistente.setDni(usuarioResponseDTO.getDni());
        usuarioExistente.setCorreo(usuarioResponseDTO.getCorreo());

        Rol rol = rolRepository.findById(usuarioResponseDTO.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuarioExistente.setRol(rol);

        Set<ProgramaAcademico> programas = usuarioResponseDTO.getProgramasAcademicos().stream()
                .map(programaDTO -> programaAcademicoRepository.findById(programaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Programa académico no encontrado")))
                .collect(Collectors.toSet());
        usuarioExistente.setProgramasAcademicos(programas);

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuarioRepository.delete(usuario);
    }



}

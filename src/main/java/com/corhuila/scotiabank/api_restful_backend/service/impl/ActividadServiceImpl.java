package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.entity.SubActividad;
import com.corhuila.scotiabank.api_restful_backend.entity.Tarea;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.SubActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.TareaDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.repository.ActividadRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.UsuarioRepository;
import com.corhuila.scotiabank.api_restful_backend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Actividad> obtenerTodasLasActividades() {
        return actividadRepository.findAll();
    }
    public List<Actividad> obtenerActividadesPorUsuario(Long usuarioId) {
        return actividadRepository.findByUsuarioId(usuarioId);
    }
    @Override
    public Actividad crearActividad(ActividadDTO actividadDTO) {
        // Buscar y asignar al usuario
        Usuario usuario = obtenerUsuarioDesdeDTO(actividadDTO.getUsuario());

        // Crear la nueva actividad
        Actividad actividad = new Actividad();
        actividad.setNombre(actividadDTO.getNombre());
        actividad.setTipoActividad(actividadDTO.getTipoActividad());
        actividad.setDescripcion(actividadDTO.getDescripcion());
        actividad.setDedicacionSemanal(actividadDTO.getDedicacionSemanal());
        actividad.setDedicacionSemestral(actividadDTO.getDedicacionSemestral());
        actividad.setUsuario(usuario);

        // Asignar las subactividades
        List<SubActividad> subActividades = actividadDTO.getSubActividades().stream()
                .map(this::convertirDTOASubActividad)
                .collect(Collectors.toList());
        actividad.setSubActividades(subActividades);

        // Guardar la actividad en la base de datos
        return actividadRepository.save(actividad);
    }

    private Usuario obtenerUsuarioDesdeDTO(UsuarioDTO usuarioDTO) {
        // Suponiendo que los usuarios ya están en la base de datos, buscar por ID
        return usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private SubActividad convertirDTOASubActividad(SubActividadDTO subActividadDTO) {
        SubActividad subActividad = new SubActividad();
        subActividad.setNombre(subActividadDTO.getNombre());
        subActividad.setDescripcion(subActividadDTO.getDescripcion());
        subActividad.setTareas(subActividadDTO.getTareas().stream()
                .map(this::convertirDTOATarea)
                .collect(Collectors.toList()));
        return subActividad;
    }

    private Tarea convertirDTOATarea(TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.setNombre(tareaDTO.getNombre());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setAsignable(tareaDTO.isAsignable());
        return tarea;
    }
}

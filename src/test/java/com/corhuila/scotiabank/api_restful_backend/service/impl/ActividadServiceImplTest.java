package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.RolDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.SubActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.repository.ActividadRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ActividadServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ActividadServiceImplTest {
    @MockBean
    private ActividadRepository actividadRepository;

    @Autowired
    private ActividadServiceImpl actividadServiceImpl;

    @MockBean
    private UsuarioRepository usuarioRepository;


    @Test
    @DisplayName("Test obtenerActividadesPorUsuario(Long); then return Empty")
    void testObtenerActividadesPorUsuario_thenReturnEmpty() {
        when(actividadRepository.findByUsuarioId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        List<Actividad> actualObtenerActividadesPorUsuarioResult = actividadServiceImpl.obtenerActividadesPorUsuario(1L);

        verify(actividadRepository).findByUsuarioId(eq(1L));
        assertTrue(actualObtenerActividadesPorUsuarioResult.isEmpty());
    }

    @Test
    @DisplayName("Test obtenerActividadesPorUsuario(Long); then throw RuntimeException")
    void testObtenerActividadesPorUsuario_thenThrowRuntimeException() {
        when(actividadRepository.findByUsuarioId(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        assertThrows(RuntimeException.class, () -> actividadServiceImpl.obtenerActividadesPorUsuario(1L));
        verify(actividadRepository).findByUsuarioId(eq(1L));
    }


    @Test
    @DisplayName("Test crearActividad(ActividadDTO); given ActividadRepository save(Object) throw RuntimeException(String) with 'foo'")
    void testCrearActividad_givenActividadRepositorySaveThrowRuntimeExceptionWithFoo() {
        when(actividadRepository.save(Mockito.<Actividad>any())).thenThrow(new RuntimeException("foo"));

        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);
        Optional<Usuario> ofResult = Optional.of(usuario);
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RolDTO rol2 = new RolDTO();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        UsuarioDTO usuario2 = new UsuarioDTO();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new ArrayList<>());
        usuario2.setRol(rol2);

        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDedicacionSemanal(new BigDecimal("2.3"));
        actividadDTO.setDedicacionSemestral(new BigDecimal("2.3"));
        actividadDTO.setDescripcion("Descripcion");
        actividadDTO.setId(1L);
        actividadDTO.setNombre("Nombre");
        actividadDTO.setSubActividades(new ArrayList<>());
        actividadDTO.setTipoActividad("Tipo Actividad");
        actividadDTO.setUsuario(usuario2);

        assertThrows(RuntimeException.class, () -> actividadServiceImpl.crearActividad(actividadDTO));
        verify(usuarioRepository).findById(eq(1L));
        verify(actividadRepository).save(isA(Actividad.class));
    }


    @Test
    @DisplayName("Test crearActividad(ActividadDTO); given SubActividadDTO (default constructor) Descripcion is '42'; then return Actividad()")
    void testCrearActividad_givenSubActividadDTODescripcionIs42_thenReturnActividad() {
        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);

        Actividad actividad = new Actividad();
        actividad.setDedicacionSemanal(new BigDecimal("2.3"));
        actividad.setDedicacionSemestral(new BigDecimal("2.3"));
        actividad.setDescripcion("Descripcion");
        actividad.setId(1L);
        actividad.setNombre("Nombre");
        actividad.setSubActividades(new ArrayList<>());
        actividad.setTipoActividad("Tipo Actividad");
        actividad.setUsuario(usuario);
        when(actividadRepository.save(Mockito.<Actividad>any())).thenReturn(actividad);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol2);
        Optional<Usuario> ofResult = Optional.of(usuario2);
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        SubActividadDTO subActividadDTO = new SubActividadDTO();
        subActividadDTO.setDescripcion("Descripcion");
        subActividadDTO.setId(1L);
        subActividadDTO.setNombre("Nombre");
        subActividadDTO.setTareas(new ArrayList<>());

        SubActividadDTO subActividadDTO2 = new SubActividadDTO();
        subActividadDTO2.setDescripcion("42");
        subActividadDTO2.setId(2L);
        subActividadDTO2.setNombre("42");
        subActividadDTO2.setTareas(new ArrayList<>());

        ArrayList<SubActividadDTO> subActividades = new ArrayList<>();
        subActividades.add(subActividadDTO2);
        subActividades.add(subActividadDTO);

        RolDTO rol3 = new RolDTO();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        UsuarioDTO usuario3 = new UsuarioDTO();
        usuario3.setActividades(new ArrayList<>());
        usuario3.setApellido("Apellido");
        usuario3.setCorreo("Correo");
        usuario3.setDni("Dni");
        usuario3.setId(1L);
        usuario3.setNombre("Nombre");
        usuario3.setProgramasAcademicos(new ArrayList<>());
        usuario3.setRol(rol3);

        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDedicacionSemanal(new BigDecimal("2.3"));
        actividadDTO.setDedicacionSemestral(new BigDecimal("2.3"));
        actividadDTO.setDescripcion("Descripcion");
        actividadDTO.setId(1L);
        actividadDTO.setNombre("Nombre");
        actividadDTO.setSubActividades(subActividades);
        actividadDTO.setTipoActividad("Tipo Actividad");
        actividadDTO.setUsuario(usuario3);

        Actividad actualCrearActividadResult = actividadServiceImpl.crearActividad(actividadDTO);

        verify(usuarioRepository).findById(eq(1L));
        verify(actividadRepository).save(isA(Actividad.class));
        assertSame(actividad, actualCrearActividadResult);
    }


    @Test
    @DisplayName("Test crearActividad(ActividadDTO); given SubActividadDTO (default constructor) Descripcion is 'Descripcion'")
    void testCrearActividad_givenSubActividadDTODescripcionIsDescripcion() {
        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);

        Actividad actividad = new Actividad();
        actividad.setDedicacionSemanal(new BigDecimal("2.3"));
        actividad.setDedicacionSemestral(new BigDecimal("2.3"));
        actividad.setDescripcion("Descripcion");
        actividad.setId(1L);
        actividad.setNombre("Nombre");
        actividad.setSubActividades(new ArrayList<>());
        actividad.setTipoActividad("Tipo Actividad");
        actividad.setUsuario(usuario);
        when(actividadRepository.save(Mockito.<Actividad>any())).thenReturn(actividad);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol2);
        Optional<Usuario> ofResult = Optional.of(usuario2);
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        SubActividadDTO subActividadDTO = new SubActividadDTO();
        subActividadDTO.setDescripcion("Descripcion");
        subActividadDTO.setId(1L);
        subActividadDTO.setNombre("Nombre");
        subActividadDTO.setTareas(new ArrayList<>());

        ArrayList<SubActividadDTO> subActividades = new ArrayList<>();
        subActividades.add(subActividadDTO);

        RolDTO rol3 = new RolDTO();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        UsuarioDTO usuario3 = new UsuarioDTO();
        usuario3.setActividades(new ArrayList<>());
        usuario3.setApellido("Apellido");
        usuario3.setCorreo("Correo");
        usuario3.setDni("Dni");
        usuario3.setId(1L);
        usuario3.setNombre("Nombre");
        usuario3.setProgramasAcademicos(new ArrayList<>());
        usuario3.setRol(rol3);

        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDedicacionSemanal(new BigDecimal("2.3"));
        actividadDTO.setDedicacionSemestral(new BigDecimal("2.3"));
        actividadDTO.setDescripcion("Descripcion");
        actividadDTO.setId(1L);
        actividadDTO.setNombre("Nombre");
        actividadDTO.setSubActividades(subActividades);
        actividadDTO.setTipoActividad("Tipo Actividad");
        actividadDTO.setUsuario(usuario3);

        Actividad actualCrearActividadResult = actividadServiceImpl.crearActividad(actividadDTO);

        verify(usuarioRepository).findById(eq(1L));
        verify(actividadRepository).save(isA(Actividad.class));
        assertSame(actividad, actualCrearActividadResult);
    }


    @Test
    @DisplayName("Test crearActividad(ActividadDTO); given UsuarioRepository findById(Object) return empty")
    void testCrearActividad_givenUsuarioRepositoryFindByIdReturnEmpty() {
        Optional<Usuario> emptyResult = Optional.empty();
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        RolDTO rol = new RolDTO();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new ArrayList<>());
        usuario.setRol(rol);

        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDedicacionSemanal(new BigDecimal("2.3"));
        actividadDTO.setDedicacionSemestral(new BigDecimal("2.3"));
        actividadDTO.setDescripcion("Descripcion");
        actividadDTO.setId(1L);
        actividadDTO.setNombre("Nombre");
        actividadDTO.setSubActividades(new ArrayList<>());
        actividadDTO.setTipoActividad("Tipo Actividad");
        actividadDTO.setUsuario(usuario);

        assertThrows(RuntimeException.class, () -> actividadServiceImpl.crearActividad(actividadDTO));
        verify(usuarioRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test crearActividad(ActividadDTO); then return Actividad()")
    void testCrearActividad_thenReturnActividad() {
        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);

        Actividad actividad = new Actividad();
        actividad.setDedicacionSemanal(new BigDecimal("2.3"));
        actividad.setDedicacionSemestral(new BigDecimal("2.3"));
        actividad.setDescripcion("Descripcion");
        actividad.setId(1L);
        actividad.setNombre("Nombre");
        actividad.setSubActividades(new ArrayList<>());
        actividad.setTipoActividad("Tipo Actividad");
        actividad.setUsuario(usuario);
        when(actividadRepository.save(Mockito.<Actividad>any())).thenReturn(actividad);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol2);
        Optional<Usuario> ofResult = Optional.of(usuario2);
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        RolDTO rol3 = new RolDTO();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        UsuarioDTO usuario3 = new UsuarioDTO();
        usuario3.setActividades(new ArrayList<>());
        usuario3.setApellido("Apellido");
        usuario3.setCorreo("Correo");
        usuario3.setDni("Dni");
        usuario3.setId(1L);
        usuario3.setNombre("Nombre");
        usuario3.setProgramasAcademicos(new ArrayList<>());
        usuario3.setRol(rol3);

        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setDedicacionSemanal(new BigDecimal("2.3"));
        actividadDTO.setDedicacionSemestral(new BigDecimal("2.3"));
        actividadDTO.setDescripcion("Descripcion");
        actividadDTO.setId(1L);
        actividadDTO.setNombre("Nombre");
        actividadDTO.setSubActividades(new ArrayList<>());
        actividadDTO.setTipoActividad("Tipo Actividad");
        actividadDTO.setUsuario(usuario3);

        Actividad actualCrearActividadResult = actividadServiceImpl.crearActividad(actividadDTO);

        verify(usuarioRepository).findById(eq(1L));
        verify(actividadRepository).save(isA(Actividad.class));
        assertSame(actividad, actualCrearActividadResult);
    }
}

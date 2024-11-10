package com.corhuila.scotiabank.api_restful_backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ProgramaAcademicoDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.RolDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioResponseDTO;
import com.corhuila.scotiabank.api_restful_backend.repository.ProgramaAcademicoRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.UsuarioRepository;
import com.corhuila.scotiabank.api_restful_backend.service.UsuarioService;
import com.corhuila.scotiabank.api_restful_backend.service.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UsuarioController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioControllerTest {
    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Test crearUsuario(UsuarioDTO); given RuntimeException(String) with 'foo'; then calls getApellido()")
    void testCrearUsuario_givenRuntimeExceptionWithFoo_thenCallsGetApellido() {

        UsuarioController usuarioController = new UsuarioController(new UsuarioServiceImpl(mock(UsuarioRepository.class),
                mock(RolRepository.class), mock(ProgramaAcademicoRepository.class)));

        RolDTO rol = new RolDTO();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        RolDTO rolDTO = new RolDTO();
        rolDTO.setAsociaciones(new ArrayList<>());
        rolDTO.setId(1L);
        rolDTO.setNombre("Nombre");
        rolDTO.setUsuarios(new ArrayList<>());
        UsuarioDTO usuarioDTO = mock(UsuarioDTO.class);
        when(usuarioDTO.getProgramasAcademicos()).thenThrow(new RuntimeException("foo"));
        when(usuarioDTO.getRol()).thenReturn(rolDTO);
        when(usuarioDTO.getApellido()).thenReturn("Apellido");
        when(usuarioDTO.getCorreo()).thenReturn("Correo");
        when(usuarioDTO.getDni()).thenReturn("Dni");
        when(usuarioDTO.getNombre()).thenReturn("Nombre");
        doNothing().when(usuarioDTO).setActividades(Mockito.<List<String>>any());
        doNothing().when(usuarioDTO).setApellido(Mockito.<String>any());
        doNothing().when(usuarioDTO).setCorreo(Mockito.<String>any());
        doNothing().when(usuarioDTO).setDni(Mockito.<String>any());
        doNothing().when(usuarioDTO).setId(Mockito.<Long>any());
        doNothing().when(usuarioDTO).setNombre(Mockito.<String>any());
        doNothing().when(usuarioDTO).setProgramasAcademicos(Mockito.<List<ProgramaAcademicoDTO>>any());
        doNothing().when(usuarioDTO).setRol(Mockito.<RolDTO>any());
        usuarioDTO.setActividades(new ArrayList<>());
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setCorreo("Correo");
        usuarioDTO.setDni("Dni");
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioDTO.setRol(rol);

        ResponseEntity<Usuario> actualCrearUsuarioResult = usuarioController.crearUsuario(usuarioDTO);

        verify(usuarioDTO).getApellido();
        verify(usuarioDTO).getCorreo();
        verify(usuarioDTO).getDni();
        verify(usuarioDTO).getNombre();
        verify(usuarioDTO).getProgramasAcademicos();
        verify(usuarioDTO).getRol();
        verify(usuarioDTO).setActividades(isA(List.class));
        verify(usuarioDTO).setApellido(eq("Apellido"));
        verify(usuarioDTO).setCorreo(eq("Correo"));
        verify(usuarioDTO).setDni(eq("Dni"));
        verify(usuarioDTO).setId(eq(1L));
        verify(usuarioDTO).setNombre(eq("Nombre"));
        verify(usuarioDTO).setProgramasAcademicos(isA(List.class));
        verify(usuarioDTO).setRol(isA(RolDTO.class));
        HttpStatusCode statusCode = actualCrearUsuarioResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCrearUsuarioResult.getBody());
        assertEquals(500, actualCrearUsuarioResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertFalse(actualCrearUsuarioResult.hasBody());
    }

    @Test
    @DisplayName("Test crearUsuario(UsuarioDTO); given UsuarioRepository save(Object) throw RuntimeException(String) with 'foo'")
    void testCrearUsuario_givenUsuarioRepositorySaveThrowRuntimeExceptionWithFoo() {

        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenThrow(new RuntimeException("foo"));

        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol);
        RolRepository rolRepository = mock(RolRepository.class);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);
        UsuarioController usuarioController = new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, rolRepository, mock(ProgramaAcademicoRepository.class)));

        RolDTO rol2 = new RolDTO();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setActividades(new ArrayList<>());
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setCorreo("Correo");
        usuarioDTO.setDni("Dni");
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioDTO.setRol(rol2);

        ResponseEntity<Usuario> actualCrearUsuarioResult = usuarioController.crearUsuario(usuarioDTO);

        verify(rolRepository).findByNombre(eq("Nombre"));
        verify(usuarioRepository).save(isA(Usuario.class));
        HttpStatusCode statusCode = actualCrearUsuarioResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualCrearUsuarioResult.getBody());
        assertEquals(500, actualCrearUsuarioResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertFalse(actualCrearUsuarioResult.hasBody());
    }

    @Test
    @DisplayName("Test crearUsuario(UsuarioDTO); then return StatusCodeValue is two hundred one")
    void testCrearUsuario_thenReturnStatusCodeValueIsTwoHundredOne() {

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
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol2);
        RolRepository rolRepository = mock(RolRepository.class);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);
        UsuarioController usuarioController = new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, rolRepository, mock(ProgramaAcademicoRepository.class)));

        RolDTO rol3 = new RolDTO();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setActividades(new ArrayList<>());
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setCorreo("Correo");
        usuarioDTO.setDni("Dni");
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioDTO.setRol(rol3);

        ResponseEntity<Usuario> actualCrearUsuarioResult = usuarioController.crearUsuario(usuarioDTO);

        verify(rolRepository).findByNombre(eq("Nombre"));
        verify(usuarioRepository).save(isA(Usuario.class));
        HttpStatusCode statusCode = actualCrearUsuarioResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(201, actualCrearUsuarioResult.getStatusCodeValue());
        assertEquals(HttpStatus.CREATED, statusCode);
        assertTrue(actualCrearUsuarioResult.hasBody());
        assertSame(usuario, actualCrearUsuarioResult.getBody());
    }


    @Test
    @DisplayName("Test obtenerUsuarios(); given Rol() Id is two; then return Body size is two")
    void testObtenerUsuarios_givenRolIdIsTwo_thenReturnBodySizeIsTwo() {

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

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(2L);
        rol2.setNombre("42");
        rol2.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("42");
        usuario2.setCorreo("42");
        usuario2.setDni("42");
        usuario2.setId(2L);
        usuario2.setNombre("42");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol2);

        ArrayList<Usuario> usuarioList = new ArrayList<>();
        usuarioList.add(usuario2);
        usuarioList.add(usuario);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.findAll()).thenReturn(usuarioList);

        ResponseEntity<List<UsuarioResponseDTO>> actualObtenerUsuariosResult = (new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, mock(RolRepository.class), mock(ProgramaAcademicoRepository.class))))
                .obtenerUsuarios();

        verify(usuarioRepository).findAll();
        List<UsuarioResponseDTO> body = actualObtenerUsuariosResult.getBody();
        assertEquals(2, body.size());
        UsuarioResponseDTO getResult = body.get(0);
        assertEquals("42", getResult.getApellido());
        assertEquals("42", getResult.getCorreo());
        assertEquals("42", getResult.getDni());
        assertEquals("42", getResult.getNombre());
        UsuarioResponseDTO.RolDTO rol3 = getResult.getRol();
        assertEquals("42", rol3.getNombre());
        UsuarioResponseDTO getResult2 = body.get(1);
        assertEquals("Apellido", getResult2.getApellido());
        assertEquals("Correo", getResult2.getCorreo());
        assertEquals("Dni", getResult2.getDni());
        assertEquals("Nombre", getResult2.getNombre());
        UsuarioResponseDTO.RolDTO rol4 = getResult2.getRol();
        assertEquals("Nombre", rol4.getNombre());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals(1L, rol4.getId().longValue());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals(2L, rol3.getId().longValue());
        assertTrue(getResult2.getProgramasAcademicos().isEmpty());
    }


    @Test
    @DisplayName("Test obtenerUsuarios(); given Usuario() Actividades is ArrayList(); then return Body size is one")
    void testObtenerUsuarios_givenUsuarioActividadesIsArrayList_thenReturnBodySizeIsOne() {

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

        ArrayList<Usuario> usuarioList = new ArrayList<>();
        usuarioList.add(usuario);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.findAll()).thenReturn(usuarioList);

        ResponseEntity<List<UsuarioResponseDTO>> actualObtenerUsuariosResult = (new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, mock(RolRepository.class), mock(ProgramaAcademicoRepository.class))))
                .obtenerUsuarios();

        verify(usuarioRepository).findAll();
        List<UsuarioResponseDTO> body = actualObtenerUsuariosResult.getBody();
        assertEquals(1, body.size());
        UsuarioResponseDTO getResult = body.get(0);
        assertEquals("Apellido", getResult.getApellido());
        assertEquals("Correo", getResult.getCorreo());
        assertEquals("Dni", getResult.getDni());
        assertEquals("Nombre", getResult.getNombre());
        UsuarioResponseDTO.RolDTO rol2 = getResult.getRol();
        assertEquals("Nombre", rol2.getNombre());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, rol2.getId().longValue());
    }


    @Test
    @DisplayName("Test obtenerUsuarios(); given UsuarioController(UsuarioService) with usuarioService is 'null'")
    void testObtenerUsuarios_givenUsuarioControllerWithUsuarioServiceIsNull() {

        ResponseEntity<List<UsuarioResponseDTO>> actualObtenerUsuariosResult = (new UsuarioController(null))
                .obtenerUsuarios();

        HttpStatusCode statusCode = actualObtenerUsuariosResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualObtenerUsuariosResult.getBody());
        assertEquals(500, actualObtenerUsuariosResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertFalse(actualObtenerUsuariosResult.hasBody());
    }

    @Test
    @DisplayName("Test obtenerUsuarios(); then calls getApellido()")
    void testObtenerUsuarios_thenCallsGetApellido() {

        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Usuario usuario = mock(Usuario.class);
        when(usuario.getProgramasAcademicos()).thenThrow(new RuntimeException("foo"));
        when(usuario.getRol()).thenReturn(rol2);
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getApellido()).thenReturn("Apellido");
        when(usuario.getCorreo()).thenReturn("Correo");
        when(usuario.getDni()).thenReturn("Dni");
        when(usuario.getNombre()).thenReturn("Nombre");
        doNothing().when(usuario).setActividades(Mockito.<List<Actividad>>any());
        doNothing().when(usuario).setApellido(Mockito.<String>any());
        doNothing().when(usuario).setCorreo(Mockito.<String>any());
        doNothing().when(usuario).setDni(Mockito.<String>any());
        doNothing().when(usuario).setId(Mockito.<Long>any());
        doNothing().when(usuario).setNombre(Mockito.<String>any());
        doNothing().when(usuario).setProgramasAcademicos(Mockito.<Set<ProgramaAcademico>>any());
        doNothing().when(usuario).setRol(Mockito.<Rol>any());
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);

        ArrayList<Usuario> usuarioList = new ArrayList<>();
        usuarioList.add(usuario);
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.findAll()).thenReturn(usuarioList);

        ResponseEntity<List<UsuarioResponseDTO>> actualObtenerUsuariosResult = (new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, mock(RolRepository.class), mock(ProgramaAcademicoRepository.class))))
                .obtenerUsuarios();

        verify(usuario).getApellido();
        verify(usuario).getCorreo();
        verify(usuario).getDni();
        verify(usuario).getId();
        verify(usuario).getNombre();
        verify(usuario).getProgramasAcademicos();
        verify(usuario, atLeast(1)).getRol();
        verify(usuario).setActividades(isA(List.class));
        verify(usuario).setApellido(eq("Apellido"));
        verify(usuario).setCorreo(eq("Correo"));
        verify(usuario).setDni(eq("Dni"));
        verify(usuario).setId(eq(1L));
        verify(usuario).setNombre(eq("Nombre"));
        verify(usuario).setProgramasAcademicos(isA(Set.class));
        verify(usuario).setRol(isA(Rol.class));
        verify(usuarioRepository).findAll();
        HttpStatusCode statusCode = actualObtenerUsuariosResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualObtenerUsuariosResult.getBody());
        assertEquals(500, actualObtenerUsuariosResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertFalse(actualObtenerUsuariosResult.hasBody());
    }

    @Test
    @DisplayName("Test obtenerUsuarios(); then return StatusCodeValue is two hundred")
    void testObtenerUsuarios_thenReturnStatusCodeValueIsTwoHundred() {

        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<UsuarioResponseDTO>> actualObtenerUsuariosResult = (new UsuarioController(
                new UsuarioServiceImpl(usuarioRepository, mock(RolRepository.class), mock(ProgramaAcademicoRepository.class))))
                .obtenerUsuarios();

        verify(usuarioRepository).findAll();
        HttpStatusCode statusCode = actualObtenerUsuariosResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(200, actualObtenerUsuariosResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(actualObtenerUsuariosResult.getBody().isEmpty());
        assertTrue(actualObtenerUsuariosResult.hasBody());
    }


    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given ProgramaAcademico() Id is one; then status isOk()")
    void testActualizarUsuario_givenProgramaAcademicoIdIsOne_thenStatusIsOk() throws Exception {
        ProgramaAcademico programaAcademico = new ProgramaAcademico();
        programaAcademico.setId(1L);
        programaAcademico.setNombre("Nombre");

        HashSet<ProgramaAcademico> programasAcademicos = new HashSet<>();
        programasAcademicos.add(programaAcademico);

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
        usuario.setProgramasAcademicos(programasAcademicos);
        usuario.setRol(rol);
        when(usuarioService.actualizarUsuario(Mockito.<Long>any(), Mockito.<UsuarioResponseDTO>any())).thenReturn(usuario);

        UsuarioResponseDTO.RolDTO rol2 = new UsuarioResponseDTO.RolDTO();
        rol2.setId(1L);
        rol2.setNombre("Nombre");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol2);
        String content = (new ObjectMapper()).writeValueAsString(usuarioResponseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"nombre\":\"Nombre\",\"apellido\":\"Apellido\",\"dni\":\"Dni\",\"correo\":\"Correo\",\"rol\":{\"id\":1,\"nombre"
                                        + "\":\"Nombre\"},\"programasAcademicos\":[{\"id\":1,\"nombre\":\"Nombre\"}]}"));
    }

    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given Rol() Asociaciones is ArrayList(); then status isOk()")
    void testActualizarUsuario_givenRolAsociacionesIsArrayList_thenStatusIsOk() throws Exception {
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
        when(usuarioService.actualizarUsuario(Mockito.<Long>any(), Mockito.<UsuarioResponseDTO>any())).thenReturn(usuario);

        UsuarioResponseDTO.RolDTO rol2 = new UsuarioResponseDTO.RolDTO();
        rol2.setId(1L);
        rol2.setNombre("Nombre");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol2);
        String content = (new ObjectMapper()).writeValueAsString(usuarioResponseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"nombre\":\"Nombre\",\"apellido\":\"Apellido\",\"dni\":\"Dni\",\"correo\":\"Correo\",\"rol\":{\"id\":1,\"nombre"
                                        + "\":\"Nombre\"},\"programasAcademicos\":[]}"));
    }

    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); then status isNotFound()")
    void testActualizarUsuario_thenStatusIsNotFound() throws Exception {
        when(usuarioService.actualizarUsuario(Mockito.<Long>any(), Mockito.<UsuarioResponseDTO>any()))
                .thenThrow(new RuntimeException("foo"));

        UsuarioResponseDTO.RolDTO rol = new UsuarioResponseDTO.RolDTO();
        rol.setId(1L);
        rol.setNombre("Nombre");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol);
        String content = (new ObjectMapper()).writeValueAsString(usuarioResponseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @DisplayName("Test eliminarUsuario(Long); then status isNoContent()")
    void testEliminarUsuario_thenStatusIsNoContent() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/usuarios/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test eliminarUsuario(Long); then status isNotFound()")
    void testEliminarUsuario_thenStatusIsNotFound() throws Exception {
        doThrow(new RuntimeException("foo")).when(usuarioService).eliminarUsuario(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/usuarios/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

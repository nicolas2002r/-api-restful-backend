package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioResponseDTO;
import com.corhuila.scotiabank.api_restful_backend.repository.ProgramaAcademicoRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UsuarioServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioServiceImplTest {
    @MockBean
    private ProgramaAcademicoRepository programaAcademicoRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Test
    @DisplayName("Test crearUsuario(Usuario, String, List); given RolRepository findByNombre(String) return empty")
    void testCrearUsuario_givenRolRepositoryFindByNombreReturnEmpty() {
        Optional<Rol> emptyResult = Optional.empty();
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(emptyResult);

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

        assertThrows(RuntimeException.class,
                () -> usuarioServiceImpl.crearUsuario(usuario, "Nombre Rol", new ArrayList<>()));
        verify(rolRepository).findByNombre(eq("Nombre Rol"));
    }

    @Test
    @DisplayName("Test crearUsuario(Usuario, String, List); given UsuarioRepository save(Object) throw RuntimeException(String) with 'foo'")
    void testCrearUsuario_givenUsuarioRepositorySaveThrowRuntimeExceptionWithFoo() {
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenThrow(new RuntimeException("foo"));

        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setActividades(new ArrayList<>());
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol2);

        assertThrows(RuntimeException.class,
                () -> usuarioServiceImpl.crearUsuario(usuario, "Nombre Rol", new ArrayList<>()));
        verify(rolRepository).findByNombre(eq("Nombre Rol"));
        verify(usuarioRepository).save(isA(Usuario.class));
    }

    @Test
    @DisplayName("Test crearUsuario(Usuario, String, List); then Usuario() ProgramasAcademicos Empty")
    void testCrearUsuario_thenUsuarioProgramasAcademicosEmpty() {
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
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol2);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        Rol rol3 = new Rol();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol3);

        Usuario actualCrearUsuarioResult = usuarioServiceImpl.crearUsuario(usuario2, "Nombre Rol", new ArrayList<>());

        verify(rolRepository).findByNombre(eq("Nombre Rol"));
        verify(usuarioRepository).save(isA(Usuario.class));
        assertTrue(usuario2.getProgramasAcademicos().isEmpty());
        assertSame(usuario, actualCrearUsuarioResult);
    }

    @Test
    @DisplayName("Test crearUsuario(Usuario, String, List); then Usuario() ProgramasAcademicos size is one")
    void testCrearUsuario_thenUsuarioProgramasAcademicosSizeIsOne() {
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
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol2);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        ProgramaAcademico programaAcademico = new ProgramaAcademico();
        programaAcademico.setId(1L);
        programaAcademico.setNombre("Nombre");
        Optional<ProgramaAcademico> ofResult2 = Optional.of(programaAcademico);
        when(programaAcademicoRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult2);

        Rol rol3 = new Rol();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setActividades(new ArrayList<>());
        usuario2.setApellido("Apellido");
        usuario2.setCorreo("Correo");
        usuario2.setDni("Dni");
        usuario2.setId(1L);
        usuario2.setNombre("Nombre");
        usuario2.setProgramasAcademicos(new HashSet<>());
        usuario2.setRol(rol3);

        ArrayList<String> nombresProgramas = new ArrayList<>();
        nombresProgramas.add("foo");

        Usuario actualCrearUsuarioResult = usuarioServiceImpl.crearUsuario(usuario2, "Nombre Rol", nombresProgramas);

        verify(programaAcademicoRepository).findByNombre(eq("foo"));
        verify(rolRepository).findByNombre(eq("Nombre Rol"));
        verify(usuarioRepository).save(isA(Usuario.class));
        assertEquals(1, usuario2.getProgramasAcademicos().size());
        assertSame(usuario, actualCrearUsuarioResult);
    }

    @Test
    @DisplayName("Test obtenerTodosLosUsuarios(); then return Empty")
    void testObtenerTodosLosUsuarios_thenReturnEmpty() {
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        List<Usuario> actualObtenerTodosLosUsuariosResult = usuarioServiceImpl.obtenerTodosLosUsuarios();

        verify(usuarioRepository).findAll();
        assertTrue(actualObtenerTodosLosUsuariosResult.isEmpty());
    }

    @Test
    @DisplayName("Test obtenerTodosLosUsuarios(); then throw RuntimeException")
    void testObtenerTodosLosUsuarios_thenThrowRuntimeException() {
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException("foo"));

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.obtenerTodosLosUsuarios());
        verify(usuarioRepository).findAll();
    }


    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given RolRepository findById(Object) return empty")
    void testActualizarUsuario_givenRolRepositoryFindByIdReturnEmpty() {
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
        Optional<Rol> emptyResult = Optional.empty();
        when(rolRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.actualizarUsuario(1L, usuarioResponseDTO));
        verify(rolRepository).findById(eq(1L));
        verify(usuarioRepository).findById(eq(1L));
    }


    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given RuntimeException(String) with 'foo'; then calls getApellido()")
    void testActualizarUsuario_givenRuntimeExceptionWithFoo_thenCallsGetApellido() {
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

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult2 = Optional.of(rol2);
        when(rolRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        UsuarioResponseDTO.RolDTO rol3 = new UsuarioResponseDTO.RolDTO();
        rol3.setId(1L);
        rol3.setNombre("Nombre");

        UsuarioResponseDTO.RolDTO rolDTO = new UsuarioResponseDTO.RolDTO();
        rolDTO.setId(1L);
        rolDTO.setNombre("Nombre");
        UsuarioResponseDTO usuarioResponseDTO = mock(UsuarioResponseDTO.class);
        when(usuarioResponseDTO.getProgramasAcademicos()).thenThrow(new RuntimeException("foo"));
        when(usuarioResponseDTO.getRol()).thenReturn(rolDTO);
        when(usuarioResponseDTO.getApellido()).thenReturn("Apellido");
        when(usuarioResponseDTO.getCorreo()).thenReturn("Correo");
        when(usuarioResponseDTO.getDni()).thenReturn("Dni");
        when(usuarioResponseDTO.getNombre()).thenReturn("Nombre");
        doNothing().when(usuarioResponseDTO).setApellido(Mockito.<String>any());
        doNothing().when(usuarioResponseDTO).setCorreo(Mockito.<String>any());
        doNothing().when(usuarioResponseDTO).setDni(Mockito.<String>any());
        doNothing().when(usuarioResponseDTO).setId(Mockito.<Long>any());
        doNothing().when(usuarioResponseDTO).setNombre(Mockito.<String>any());
        doNothing().when(usuarioResponseDTO)
                .setProgramasAcademicos(Mockito.<List<UsuarioResponseDTO.ProgramaAcademicoDTO>>any());
        doNothing().when(usuarioResponseDTO).setRol(Mockito.<UsuarioResponseDTO.RolDTO>any());
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol3);

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.actualizarUsuario(1L, usuarioResponseDTO));
        verify(usuarioResponseDTO).getApellido();
        verify(usuarioResponseDTO).getCorreo();
        verify(usuarioResponseDTO).getDni();
        verify(usuarioResponseDTO).getNombre();
        verify(usuarioResponseDTO).getProgramasAcademicos();
        verify(usuarioResponseDTO).getRol();
        verify(usuarioResponseDTO).setApellido(eq("Apellido"));
        verify(usuarioResponseDTO).setCorreo(eq("Correo"));
        verify(usuarioResponseDTO).setDni(eq("Dni"));
        verify(usuarioResponseDTO).setId(eq(1L));
        verify(usuarioResponseDTO).setNombre(eq("Nombre"));
        verify(usuarioResponseDTO).setProgramasAcademicos(isA(List.class));
        verify(usuarioResponseDTO).setRol(isA(UsuarioResponseDTO.RolDTO.class));
        verify(rolRepository).findById(eq(1L));
        verify(usuarioRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given UsuarioRepository findById(Object) return empty")
    void testActualizarUsuario_givenUsuarioRepositoryFindByIdReturnEmpty() {
        Optional<Usuario> emptyResult = Optional.empty();
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Rol rol = new Rol();
        rol.setAsociaciones(new ArrayList<>());
        rol.setId(1L);
        rol.setNombre("Nombre");
        rol.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult = Optional.of(rol);
        when(rolRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.actualizarUsuario(1L, usuarioResponseDTO));
        verify(usuarioRepository).findById(eq(1L));
    }


    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given UsuarioRepository save(Object) return Usuario(); then return Usuario()")
    void testActualizarUsuario_givenUsuarioRepositorySaveReturnUsuario_thenReturnUsuario() {
        // Arrange
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
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario2);
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Rol rol3 = new Rol();
        rol3.setAsociaciones(new ArrayList<>());
        rol3.setId(1L);
        rol3.setNombre("Nombre");
        rol3.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult2 = Optional.of(rol3);
        when(rolRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        UsuarioResponseDTO.RolDTO rol4 = new UsuarioResponseDTO.RolDTO();
        rol4.setId(1L);
        rol4.setNombre("Nombre");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol4);

        Usuario actualActualizarUsuarioResult = usuarioServiceImpl.actualizarUsuario(1L, usuarioResponseDTO);

        verify(rolRepository).findById(eq(1L));
        verify(usuarioRepository).findById(eq(1L));
        verify(usuarioRepository).save(isA(Usuario.class));
        assertSame(usuario2, actualActualizarUsuarioResult);
    }

    @Test
    @DisplayName("Test actualizarUsuario(Long, UsuarioResponseDTO); given UsuarioRepository save(Object) throw RuntimeException(String) with 'foo'")
    void testActualizarUsuario_givenUsuarioRepositorySaveThrowRuntimeExceptionWithFoo() {
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
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenThrow(new RuntimeException("foo"));
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Rol rol2 = new Rol();
        rol2.setAsociaciones(new ArrayList<>());
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        rol2.setUsuarios(new ArrayList<>());
        Optional<Rol> ofResult2 = Optional.of(rol2);
        when(rolRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        UsuarioResponseDTO.RolDTO rol3 = new UsuarioResponseDTO.RolDTO();
        rol3.setId(1L);
        rol3.setNombre("Nombre");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setApellido("Apellido");
        usuarioResponseDTO.setCorreo("Correo");
        usuarioResponseDTO.setDni("Dni");
        usuarioResponseDTO.setId(1L);
        usuarioResponseDTO.setNombre("Nombre");
        usuarioResponseDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioResponseDTO.setRol(rol3);

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.actualizarUsuario(1L, usuarioResponseDTO));
        verify(rolRepository).findById(eq(1L));
        verify(usuarioRepository).findById(eq(1L));
        verify(usuarioRepository).save(isA(Usuario.class));
    }


    @Test
    @DisplayName("Test eliminarUsuario(Long); given UsuarioRepository delete(Object) does nothing; then calls delete(Object)")
    void testEliminarUsuario_givenUsuarioRepositoryDeleteDoesNothing_thenCallsDelete() {
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
        doNothing().when(usuarioRepository).delete(Mockito.<Usuario>any());
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        usuarioServiceImpl.eliminarUsuario(1L);

        verify(usuarioRepository).delete(isA(Usuario.class));
        verify(usuarioRepository).findById(eq(1L));
    }


    @Test
    @DisplayName("Test eliminarUsuario(Long); given UsuarioRepository delete(Object) throw RuntimeException(String) with 'foo'")
    void testEliminarUsuario_givenUsuarioRepositoryDeleteThrowRuntimeExceptionWithFoo() {
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
        doThrow(new RuntimeException("foo")).when(usuarioRepository).delete(Mockito.<Usuario>any());
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.eliminarUsuario(1L));
        verify(usuarioRepository).delete(isA(Usuario.class));
        verify(usuarioRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test eliminarUsuario(Long); given UsuarioRepository findById(Object) return empty")
    void testEliminarUsuario_givenUsuarioRepositoryFindByIdReturnEmpty() {
        Optional<Usuario> emptyResult = Optional.empty();
        when(usuarioRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        assertThrows(RuntimeException.class, () -> usuarioServiceImpl.eliminarUsuario(1L));
        verify(usuarioRepository).findById(eq(1L));
    }
}

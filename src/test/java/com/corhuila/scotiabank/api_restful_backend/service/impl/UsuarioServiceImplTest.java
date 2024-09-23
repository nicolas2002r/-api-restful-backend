package com.corhuila.scotiabank.api_restful_backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.corhuila.scotiabank.api_restful_backend.entity.ProgramaAcademico;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.repository.ProgramaAcademicoRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;
import com.corhuila.scotiabank.api_restful_backend.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    void testCrearUsuario() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Nombre");

        Usuario usuario = new Usuario();
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Rol rol2 = new Rol();
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        Optional<Rol> ofResult = Optional.of(rol2);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        Rol rol3 = new Rol();
        rol3.setId(1L);
        rol3.setNombre("Nombre");

        Usuario usuario2 = new Usuario();
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
    void testCrearUsuario2() {
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenThrow(new RuntimeException("foo"));

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Nombre");
        Optional<Rol> ofResult = Optional.of(rol);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        Rol rol2 = new Rol();
        rol2.setId(1L);
        rol2.setNombre("Nombre");

        Usuario usuario = new Usuario();
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
    void testCrearUsuario3() {
        Optional<Rol> emptyResult = Optional.empty();
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(emptyResult);

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Nombre");

        Usuario usuario = new Usuario();
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
    void testCrearUsuario4() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Nombre");

        Usuario usuario = new Usuario();
        usuario.setApellido("Apellido");
        usuario.setCorreo("Correo");
        usuario.setDni("Dni");
        usuario.setId(1L);
        usuario.setNombre("Nombre");
        usuario.setProgramasAcademicos(new HashSet<>());
        usuario.setRol(rol);
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        Rol rol2 = new Rol();
        rol2.setId(1L);
        rol2.setNombre("Nombre");
        Optional<Rol> ofResult = Optional.of(rol2);
        when(rolRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult);

        ProgramaAcademico programaAcademico = new ProgramaAcademico();
        programaAcademico.setId(1L);
        programaAcademico.setNombre("Nombre");
        Optional<ProgramaAcademico> ofResult2 = Optional.of(programaAcademico);
        when(programaAcademicoRepository.findByNombre(Mockito.<String>any())).thenReturn(ofResult2);

        Rol rol3 = new Rol();
        rol3.setId(1L);
        rol3.setNombre("Nombre");

        Usuario usuario2 = new Usuario();
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
}

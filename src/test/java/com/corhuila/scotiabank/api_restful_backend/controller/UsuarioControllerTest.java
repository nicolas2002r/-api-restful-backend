package com.corhuila.scotiabank.api_restful_backend.controller;

import static org.mockito.Mockito.when;

import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    void testCrearUsuario() throws Exception {
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
        when(usuarioService.crearUsuario(Mockito.<Usuario>any(), Mockito.<String>any(), Mockito.<List<String>>any()))
                .thenReturn(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setCorreo("Correo");
        usuarioDTO.setDni("Dni");
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setProgramasAcademicos(new ArrayList<>());
        usuarioDTO.setRolNombre("Rol Nombre");
        String content = (new ObjectMapper()).writeValueAsString(usuarioDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"nombre\":\"Nombre\",\"apellido\":\"Apellido\",\"dni\":\"Dni\",\"correo\":\"Correo\",\"rol\":{\"id\":1,\"nombre\""
                                        + ":\"Nombre\"},\"programasAcademicos\":[]}"));
    }
}

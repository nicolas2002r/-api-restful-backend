package com.corhuila.scotiabank.api_restful_backend.controller;

import static org.mockito.Mockito.when;

import com.corhuila.scotiabank.api_restful_backend.entity.Actividad;
import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.entity.Usuario;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.ActividadDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.RolDTO;
import com.corhuila.scotiabank.api_restful_backend.entity.dto.UsuarioDTO;
import com.corhuila.scotiabank.api_restful_backend.service.ActividadService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
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

@ContextConfiguration(classes = {ActividadController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ActividadControllerTest {
    @Autowired
    private ActividadController actividadController;

    @MockBean
    private ActividadService actividadService;

    @Test
    @DisplayName("Test listarActividadesPorUsuario(Long)")
    void testListarActividadesPorUsuario() throws Exception {
        // Arrange
        when(actividadService.obtenerActividadesPorUsuario(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/actividades/usuario/{usuarioId}",
                1L);

        MockMvcBuilders.standaloneSetup(actividadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    @DisplayName("Test listarActividades()")
    void testListarActividades() throws Exception {
        when(actividadService.obtenerTodasLasActividades()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/actividades");

        MockMvcBuilders.standaloneSetup(actividadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    @DisplayName("Test crearActividad(ActividadDTO)")
    void testCrearActividad() throws Exception {
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
        when(actividadService.crearActividad(Mockito.<ActividadDTO>any())).thenReturn(actividad);

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
        String content = (new ObjectMapper()).writeValueAsString(actividadDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/actividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(actividadController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"nombre\":\"Nombre\",\"tipoActividad\":\"Tipo Actividad\",\"descripcion\":\"Descripcion\",\"dedicacionSemanal"
                                        + "\":2.3,\"dedicacionSemestral\":2.3,\"usuario\":{\"id\":1,\"nombre\":\"Nombre\",\"apellido\":\"Apellido\",\"dni\":\"Dni"
                                        + "\",\"correo\":\"Correo\",\"rol\":{\"id\":1,\"nombre\":\"Nombre\",\"usuarios\":[],\"asociaciones\":[]},\"programasAcademicos"
                                        + "\":[],\"actividades\":[]},\"subActividades\":[]}"));
    }
}

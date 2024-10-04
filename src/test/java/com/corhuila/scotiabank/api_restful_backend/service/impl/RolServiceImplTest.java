package com.corhuila.scotiabank.api_restful_backend.service.impl;

import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;
import com.corhuila.scotiabank.api_restful_backend.service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class RolServiceImplTest {
    // Simulamos el repositorio
    @Mock
    private RolRepository rolRepository;

    // Inyectamos el servicio que vamos a probar
    @InjectMocks
    private RolService rolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodosLosRoles() {
        // Datos simulados para el repositorio
        Rol rol1 = new Rol(1L, "ADMIN");
        Rol rol2 = new Rol(2L, "USER");
        List<Rol> mockRoles = Arrays.asList(rol1, rol2);

        // Configuramos el mock del repositorio para que devuelva los roles simulados
        when(rolRepository.findAll()).thenReturn(mockRoles);

        // Ejecutamos el método de prueba
        List<Rol> roles = rolService.obtenerTodosLosRoles();

        // Verificamos que el resultado es el esperado
        assertEquals(2, roles.size());
        assertEquals("ADMIN", roles.get(0).getNombre());
        assertEquals("USER", roles.get(1).getNombre());
    }
}
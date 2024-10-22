package com.corhuila.scotiabank.api_restful_backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.corhuila.scotiabank.api_restful_backend.entity.Rol;
import com.corhuila.scotiabank.api_restful_backend.repository.RolRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RolServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RolServiceImplTest {
    @MockBean
    private RolRepository rolRepository;

    @Autowired
    private RolServiceImpl rolServiceImpl;

    /**
     * Method under test: {@link RolServiceImpl#obtenerTodosLosRoles()}
     */
    @Test
    void testObtenerTodosLosRoles() {
        ArrayList<Rol> rolList = new ArrayList<>();
        when(rolRepository.findAll()).thenReturn(rolList);

        List<Rol> actualObtenerTodosLosRolesResult = rolServiceImpl.obtenerTodosLosRoles();

        verify(rolRepository).findAll();
        assertTrue(actualObtenerTodosLosRolesResult.isEmpty());
        assertSame(rolList, actualObtenerTodosLosRolesResult);
    }
}

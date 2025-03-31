package com.soofttech.soof.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.service.ServicioTransferencia;

@ExtendWith(MockitoExtension.class)
class TransferenciaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServicioTransferencia servicioTransferencia;

    @InjectMocks
    private TransferenciaController transferenciaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferenciaController).build();
    }

    @Test
    void testObtenerEmpresasConTransferenciasUltimoMes() throws Exception {
        // Crear empresas
        Empresa empresa1 = new Empresa();
        empresa1.setId(1L);
        empresa1.setCuil("20-12345678-9");
        empresa1.setRazonSocial("Empresa A");
        empresa1.setFechaAdhesion(LocalDateTime.now().minusMonths(2));

        Empresa empresa2 = new Empresa();
        empresa2.setId(2L);
        empresa2.setCuil("30-98765432-1");
        empresa2.setRazonSocial("Empresa B");
        empresa2.setFechaAdhesion(LocalDateTime.now().minusMonths(1));

        // Simulamos que el servicio devuelve una lista de empresas con transferencias en el último mes
        List<Empresa> empresasMock = Arrays.asList(empresa1, empresa2);
        when(servicioTransferencia.obtenerEmpresasConTransferenciasUltimoMes()).thenReturn(empresasMock);

        // Realizamos la solicitud GET y verificamos la respuesta
        mockMvc.perform(get("/transferencia/transferenciasUltimoMes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica que el status sea 200 OK
                .andExpect(jsonPath("$.size()").value(2)) // Verifica que se devuelven 2 empresas
                .andExpect(jsonPath("$[0].razonSocial").value("Empresa A")) // Verifica la razón social de la primera empresa
                .andExpect(jsonPath("$[1].razonSocial").value("Empresa B")); // Verifica la razón social de la segunda empresa

        // Verificamos que el servicio haya sido llamado correctamente
        verify(servicioTransferencia).obtenerEmpresasConTransferenciasUltimoMes();
    }
}

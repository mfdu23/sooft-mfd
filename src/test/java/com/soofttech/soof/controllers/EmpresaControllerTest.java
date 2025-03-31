package com.soofttech.soof.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.service.ServicioEmpresa;

@ExtendWith(MockitoExtension.class)
class EmpresaControllerTest {

	private MockMvc mockMvc;

    @Mock
    private ServicioEmpresa servicioEmpresa;

    @InjectMocks
    private EmpresaController empresaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(empresaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
	void testCrear() throws JsonProcessingException, Exception {
    	Empresa empresa = new Empresa();
        empresa.setCuil("20-12345678-9");
        empresa.setRazonSocial("Empresa Test");
        empresa.setFechaAdhesion(LocalDateTime.now());

        // Simular el servicio sin hacer nada
        doNothing().when(servicioEmpresa).crear(any(Empresa.class));

        // Realizamos la solicitud POST y verificamos el resultado
        mockMvc.perform(post("/empresa/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk());  // Verifica que la respuesta tiene status 200 OK

        // Verificamos que el servicio fue llamado
        verify(servicioEmpresa).crear(any(Empresa.class)); 
	}

	@Test
	void testObtenerEmpresasConTransferenciasUltimoMes() throws Exception {
		// Simular empresas con adhesión en el último mes
        Empresa empresa1 = new Empresa(1L, "20-12345678-9", "Empresa A", LocalDateTime.now().minusDays(10));
        Empresa empresa2 = new Empresa(2L, "30-98765432-1", "Empresa B", LocalDateTime.now().minusDays(15));

        List<Empresa> empresasMock = Arrays.asList(empresa1, empresa2);

        // Simular la respuesta del servicio
        when(servicioEmpresa.obtenerEmpresasFechaAdhesionUltimoMes()).thenReturn(empresasMock);

        // Realizamos la solicitud GET y verificamos la respuesta
        mockMvc.perform(get("/empresa/adhesionesUltimoMes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verifica que el status sea 200 OK
                .andExpect(jsonPath("$.size()").value(2)) // Verifica el tamaño de la lista
                .andExpect(jsonPath("$[0].razonSocial").value("Empresa A")) // Verifica el primer elemento
                .andExpect(jsonPath("$[1].razonSocial").value("Empresa B")); // Verifica el segundo elemento

        // Verificamos que el servicio haya sido llamado
        verify(servicioEmpresa).obtenerEmpresasFechaAdhesionUltimoMes();
	}

}

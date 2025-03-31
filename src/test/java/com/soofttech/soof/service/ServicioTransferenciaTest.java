package com.soofttech.soof.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.models.Transferencia;
import com.soofttech.soof.repositories.RepositorioTransferencia;

@ExtendWith(MockitoExtension.class)
class ServicioTransferenciaTest {

	@Mock
    private RepositorioTransferencia repositorioTransferencia;

    @InjectMocks
    private ServicioTransferencia servicioTransferencia;
	
	@Test
	void testObtenerEmpresasConTransferenciasUltimoMes() {
		 // Simulamos la fecha límite de hace un mes
        LocalDateTime fechaLimite = LocalDateTime.now().minusMonths(1);

        // Creamos empresas simuladas
        Empresa empresa1 = new Empresa(1L, "20-12345678-9", "Empresa A", LocalDateTime.now().minusDays(50));
        Empresa empresa2 = new Empresa(2L, "30-98765432-1", "Empresa B", LocalDateTime.now().minusDays(20));

        // Creamos transferencias recientes para cada empresa
        Transferencia transferencia1 = new Transferencia();
        transferencia1.setId(1L);
        transferencia1.setEmpresa(empresa1);
        transferencia1.setImporte(new BigDecimal("10000.00"));
        transferencia1.setFechaTransferencia(LocalDateTime.now().minusDays(10));
        transferencia1.setCuentaDebito("12345678901234567890");
        transferencia1.setCuentaCredito("09876543210987654321");

        Transferencia transferencia2 = new Transferencia();
        transferencia2.setId(2L);
        transferencia2.setEmpresa(empresa2);
        transferencia2.setImporte(new BigDecimal("5000.00"));
        transferencia2.setFechaTransferencia(LocalDateTime.now().minusDays(15));
        transferencia2.setCuentaDebito("12345678901234567891");
        transferencia2.setCuentaCredito("09876543210987654322");

        // Simulamos que el repositorio devuelve las empresas con transferencias recientes
        when(repositorioTransferencia.obtenerEmpresasConTransferenciasUltimoMes(any(LocalDateTime.class)))
            .thenReturn(Arrays.asList(empresa1, empresa2));

        // Llamamos al método del servicio
        List<Empresa> resultado = servicioTransferencia.obtenerEmpresasConTransferenciasUltimoMes();

        // Validamos que la lista no es nula y tiene los datos esperados
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Empresa A", resultado.get(0).getRazonSocial());
        assertEquals("Empresa B", resultado.get(1).getRazonSocial());

        // Verificamos que el repositorio fue llamado correctamente con cualquier fecha
        verify(repositorioTransferencia).obtenerEmpresasConTransferenciasUltimoMes(any(LocalDateTime.class));
	}

}

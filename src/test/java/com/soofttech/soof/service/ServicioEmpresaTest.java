package com.soofttech.soof.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.repositories.RepositorioEmpresa;

@ExtendWith(MockitoExtension.class)
class ServicioEmpresaTest {
	
	@Mock
    private RepositorioEmpresa repositorioEmpresa;

    @InjectMocks
    private ServicioEmpresa servicioEmpresa;

	@Test
	void testCrear() {
        Empresa empresa = new Empresa();
        empresa.setCuil("20-30087485-3");
        empresa.setRazonSocial("Empresa Test");
        empresa.setFechaAdhesion(LocalDateTime.now());

        // Simulamos que el repositorio guarda la empresa y le asigna un ID
        Empresa empresaGuardada = new Empresa();
        empresaGuardada.setId(1L); // Simulamos que Hibernate le asigna un ID
        when(repositorioEmpresa.save(empresa)).thenReturn(empresaGuardada);

        servicioEmpresa.crear(empresa);

        assertNotNull(empresaGuardada.getId());
    }

	@Test
	void testObtenerEmpresasFechaAdhesionUltimoMes() {
		// Fecha límite de adhesión (hace un mes)
	    @SuppressWarnings("unused")
		LocalDateTime fechaLimite = LocalDateTime.now().minusMonths(1);

	    // Lista simulada de empresas adheridas dentro del último mes
	    List<Empresa> empresasMock = Arrays.asList(
	        new Empresa(1L, "20-12345678-9", "Empresa 1", LocalDateTime.now().minusDays(10)),
	        new Empresa(2L, "30-98765432-1", "Empresa 2", LocalDateTime.now().minusDays(20))
	    );

	    // Simulamos la respuesta del repositorio cuando se consulta con la fecha límite
	    when(repositorioEmpresa.obtenerEmpresasFechaAdhesionUltimoMes(any(LocalDateTime.class)))
	        .thenReturn(empresasMock);

	    // Llamamos al método que estamos probando
	    List<Empresa> resultado = servicioEmpresa.obtenerEmpresasFechaAdhesionUltimoMes();

	    // Validamos que la respuesta no es nula y que tiene los datos esperados
	    assertNotNull(resultado);
	    assertEquals(2, resultado.size());
	    assertEquals("Empresa 1", resultado.get(0).getRazonSocial());
	    assertEquals("Empresa 2", resultado.get(1).getRazonSocial());

	    // Verificamos que el repositorio haya sido llamado con cualquier fecha LocalDateTime
	    verify(repositorioEmpresa).obtenerEmpresasFechaAdhesionUltimoMes(any(LocalDateTime.class));
	}

}

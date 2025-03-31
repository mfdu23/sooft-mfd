package com.soofttech.soof.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.repositories.RepositorioEmpresa;

@Service
public class ServicioEmpresa {
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	public void crear(Empresa empresa) {		
		repositorioEmpresa.save(empresa);
	}
	
	public List<Empresa> obtenerEmpresasFechaAdhesionUltimoMes() {
		LocalDateTime fechaLimite = LocalDateTime.now().minusMonths(1);
        
		return repositorioEmpresa.obtenerEmpresasFechaAdhesionUltimoMes(fechaLimite);		
	}
	
}

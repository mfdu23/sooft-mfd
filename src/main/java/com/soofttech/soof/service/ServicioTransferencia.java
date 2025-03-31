package com.soofttech.soof.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.repositories.RepositorioTransferencia;

@Service
public class ServicioTransferencia {
	
	@Autowired
	private RepositorioTransferencia repositorioTransferencia;
	
	public List<Empresa> obtenerEmpresasConTransferenciasUltimoMes() {
		LocalDateTime fechaLimite = LocalDateTime.now().minusMonths(1);
        
		return repositorioTransferencia.obtenerEmpresasConTransferenciasUltimoMes(fechaLimite);		
	}
}

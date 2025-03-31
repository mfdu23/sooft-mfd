package com.soofttech.soof.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.service.ServicioTransferencia;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {
	
	@Autowired
	private ServicioTransferencia servicioTransferencia;
	
	@GetMapping("/transferenciasUltimoMes")
	public ResponseEntity<List<Empresa>> obtenerEmpresasConTransferenciasUltimoMes(){
		return ResponseEntity.ok(servicioTransferencia.obtenerEmpresasConTransferenciasUltimoMes());
	}
	
}

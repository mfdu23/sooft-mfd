package com.soofttech.soof.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.service.ServicioEmpresa;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	private ServicioEmpresa servicioEmpresa;
	
	@PostMapping("/crear")
	public String crear(@RequestBody Empresa empresa) {
		servicioEmpresa.crear(empresa);
		return "Usuario Creado";
	}
	
	@GetMapping("/adhesionesUltimoMes")
	public ResponseEntity<List<Empresa>> obtenerEmpresasConTransferenciasUltimoMes(){
		return ResponseEntity.ok(servicioEmpresa.obtenerEmpresasFechaAdhesionUltimoMes());
	}
	
}

package com.soofttech.soof.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soofttech.soof.models.Empresa;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long>{
	@Query("SELECT e FROM Empresa e WHERE e.fechaAdhesion >= :fechaLimite")
	List<Empresa> obtenerEmpresasFechaAdhesionUltimoMes(@Param("fechaLimite") LocalDateTime fechaLimite);
}

package com.soofttech.soof.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soofttech.soof.models.Empresa;
import com.soofttech.soof.models.Transferencia;

public interface RepositorioTransferencia extends JpaRepository<Transferencia, Long> {
	@Query("SELECT DISTINCT e FROM Empresa e JOIN Transferencia t ON t.empresa.id = e.id WHERE t.fechaTransferencia >= :fechaLimite")
	List<Empresa> obtenerEmpresasConTransferenciasUltimoMes(@Param("fechaLimite") LocalDateTime fechaLimite);
}

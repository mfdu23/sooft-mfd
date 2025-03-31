package com.soofttech.soof.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empresa {
	private Long id;
	private String cuil;
	private String razonSocial;
	private LocalDateTime fechaAdhesion;
	
	public Empresa() {
		super();
	}
	
	public Empresa(Long id, String cuil, String razonSocial, LocalDateTime fechaAdhesion) {
		super();
		this.id = id;
		this.cuil = cuil;
		this.razonSocial = razonSocial;
		this.fechaAdhesion = fechaAdhesion;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 13)
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	
	@Column(length = 100)
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public LocalDateTime getFechaAdhesion() {
		return fechaAdhesion;
	}
	public void setFechaAdhesion(LocalDateTime fechaAdhesion) {
		this.fechaAdhesion = fechaAdhesion;
	}
	
}

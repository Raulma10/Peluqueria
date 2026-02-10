package com.tfg.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="citas")
public class Cita {

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(nullable=false)
	private LocalDate fecha;
	@Column(nullable=false)
	private LocalTime horaInicio;
	@Column(nullable=false)
	private LocalTime horaFinal;
	
	@ManyToOne
	@JoinColumn(name="idUsuario", nullable=false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name="idServicio", nullable=false)
	private Servicio servicio;
	
	//CONSTRUCTORES
	public Cita(LocalDate fecha, LocalTime horaInicio, LocalTime horaFinal, Usuario cliente, Servicio servicio) {
		super();
		this.fecha=fecha;
		this.horaInicio=horaInicio;
		this.horaFinal=horaFinal;
		this.cliente=cliente;
		this.servicio=servicio;
	}
	
	public Cita() {}

	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
}

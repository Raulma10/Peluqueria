package com.tfg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="servicios")
public class Servicio {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idServicio")
	private int id;
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private double precio;

	public Servicio(String nombre, double precio) {
		super();
		this.nombre=nombre;
		this.precio=precio;
	}
	public Servicio() {}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public double getPrecio() {return precio;}
	public void setPrecio(double precio) {this.precio = precio;}

}

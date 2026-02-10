package com.tfg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;


@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUsuario")
	private int id;
	
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String apellido;
	@Column(unique=true, nullable=false)
	private String email;
	@Column(nullable=false)
	private String contraseña;
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Rol rol;

	//CONSTRUCTORES
	public Usuario(String nombre, String apellido, String email, String contraseña, Rol rol) {
		this.nombre=nombre;
		this.apellido=apellido;
		this.email=email;
		this.contraseña=contraseña;
		this.rol=rol;
	}
	
	public Usuario() {}

	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	//DEBERIA AÑADIR UN METODO PARA HACER QUE LA CONTRASEÑA SEA MAS SEGURA
	//PERO DE MOMENTO NO ME INTERESA PARA PODER HACER PRUEBAS MAS FACILMENTE
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	

}

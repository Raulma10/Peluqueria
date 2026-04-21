package com.tfg.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario  implements UserDetails{

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
	@Column(name="password", nullable=false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Rol rol;

	public Usuario(String nombre, String apellido, String email, String password, Rol rol) {
		this.nombre=nombre;
		this.apellido=apellido;
		this.email=email;
		this.password=password;
		this.rol=rol;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getApellido() {return apellido;}
	public void setApellido(String apellido) {this.apellido = apellido;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public Rol getRol() {return rol;}
	public void setRol(Rol rol) {this.rol = rol;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return List.of(new SimpleGrantedAuthority(rol.name()));}
	@Override
	public boolean isAccountNonExpired(){return true;}
	@Override
	public boolean isAccountNonLocked(){return true;}
	@Override
	public boolean isCredentialsNonExpired(){return true;}
	@Override
	public boolean isEnabled(){return true;}
    @Override
    public String getUsername() {return email;}
}

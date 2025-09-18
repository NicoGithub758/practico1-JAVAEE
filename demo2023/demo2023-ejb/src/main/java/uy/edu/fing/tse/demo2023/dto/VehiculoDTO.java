package uy.edu.fing.tse.demo2023.dto;

import uy.edu.fing.tse.demo2023.entity.Vehiculo;

public class VehiculoDTO {
	
	private int id;
	private int anio;
	private String marca;
	private String matricula;
	private String modelo;
	
	public VehiculoDTO () {
		
	}
	
	public VehiculoDTO(Vehiculo v) {
		
		this.id = v.getId();
		this.anio = v.getAnio();
		this.marca = v.getMarca();
		this.matricula = v.getMatricula();
		this.modelo = v.getModelo();
	}
	
	public VehiculoDTO (int id, int anio, String marca, String matricula, String modelo) {
		this.id = id;
		this.anio = anio;
		this.marca = marca;
		this.matricula = matricula;
		this.modelo = modelo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

}

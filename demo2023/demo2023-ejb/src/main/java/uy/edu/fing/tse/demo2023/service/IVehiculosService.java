package uy.edu.fing.tse.demo2023.service;

import javax.ejb.Local;
import javax.persistence.NoResultException;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;
import uy.edu.fing.tse.demo2023.model.Vehiculos;

@Local
public interface IVehiculosService {

	public Vehiculos obtenerVehiculos();
	
	public Vehiculos obtenerVehiculosPaginados(int pageSize, int currentPage);
	
	public VehiculoDTO obtenerVehiculo(int id) throws NoResultException;
	
}

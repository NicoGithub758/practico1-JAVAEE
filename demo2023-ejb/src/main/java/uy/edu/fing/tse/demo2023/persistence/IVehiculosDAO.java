package uy.edu.fing.tse.demo2023.persistence;

import java.util.ArrayList;

import javax.ejb.Local;
import javax.persistence.NoResultException;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;

@Local
public interface IVehiculosDAO {

	public ArrayList<VehiculoDTO> obtenerVehiculos();
	
	public int obtenerCantidadFilas();
	
	public ArrayList<VehiculoDTO> obtenerVehiculosPaginados(int pageSize, int currentPage);
	
	public VehiculoDTO obtenerVehiculo(int id) throws NoResultException;
	
	
}

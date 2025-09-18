package uy.edu.fing.tse.demo2023.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;
import uy.edu.fing.tse.demo2023.model.Vehiculos;
import uy.edu.fing.tse.demo2023.persistence.IVehiculosDAO;
import uy.edu.fing.tse.demo2023.service.IVehiculosService;

@Stateless
@Named("vehiculosService")
public class VehiculosService implements IVehiculosService {
	
	@EJB
	IVehiculosDAO vehiculosDAO;

	@Override
	public Vehiculos obtenerVehiculos() {
		
		Vehiculos v = new Vehiculos();
		v.setListaVehiculos(vehiculosDAO.obtenerVehiculos());
		
		
		return v;
	}

	@Override
	public Vehiculos obtenerVehiculosPaginados(int pageSize, int currentPage) {
		Vehiculos v = new Vehiculos();
		v.setListaVehiculos(vehiculosDAO.obtenerVehiculosPaginados(pageSize, currentPage));
		v.setTotalRows(vehiculosDAO.obtenerCantidadFilas());
		
		return v;
	}

	@Override
	public VehiculoDTO obtenerVehiculo(int id) throws NoResultException {
		VehiculoDTO v = vehiculosDAO.obtenerVehiculo(id);
		
		return v;
	}

}

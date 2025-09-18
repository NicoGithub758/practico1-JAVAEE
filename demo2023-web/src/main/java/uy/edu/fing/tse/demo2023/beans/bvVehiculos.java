package uy.edu.fing.tse.demo2023.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;
import uy.edu.fing.tse.demo2023.model.Vehiculos;
import uy.edu.fing.tse.demo2023.service.IVehiculosService;

@Named("dtBasicView")
@ViewScoped
public class bvVehiculos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	IVehiculosService service;
	

	private Vehiculos vehiculos;
	private ArrayList<VehiculoDTO> list;
	
	@PostConstruct
	public void init() {
		
		vehiculos = service.obtenerVehiculos();
		list = vehiculos.getListaVehiculos();
	}
	
	public ArrayList<VehiculoDTO> getVehiculos() {
		return list;
	}
	
	public ArrayList<VehiculoDTO> getList() {
		return list;
	}
	
	
}

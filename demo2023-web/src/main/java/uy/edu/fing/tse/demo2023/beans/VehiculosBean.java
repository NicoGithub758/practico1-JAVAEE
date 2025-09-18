package uy.edu.fing.tse.demo2023.beans;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.inject.Named;

import uy.edu.fing.tse.demo2023.model.Vehiculos;
import uy.edu.fing.tse.demo2023.service.IVehiculosService;
import javax.annotation.*;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;

@Named("vehiculos")
@RequestScoped
public class VehiculosBean {

	@EJB
	IVehiculosService service;
	
	private Vehiculos vehiculos;
	
	private ArrayList<VehiculoDTO> list;
	
	public VehiculosBean() {
		
	}
	

	@PostConstruct
	public void init() {
		vehiculos = service.obtenerVehiculos();
		list = vehiculos.getListaVehiculos();
	}
	
	public ArrayList<VehiculoDTO> getList() {
		return list;
	}

	
}

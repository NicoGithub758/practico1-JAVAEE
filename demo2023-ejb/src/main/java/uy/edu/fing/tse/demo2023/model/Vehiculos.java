package uy.edu.fing.tse.demo2023.model;

import java.io.Serializable;
import java.util.ArrayList;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;

public class Vehiculos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<VehiculoDTO> listaVehiculos;
	private int totalRows;
	
	public ArrayList<VehiculoDTO> getListaVehiculos() {
		return listaVehiculos;
	}
	public void setListaVehiculos(ArrayList<VehiculoDTO> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}

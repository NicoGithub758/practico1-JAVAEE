package uy.edu.fing.tse.demo2023.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;
import uy.edu.fing.tse.demo2023.entity.Vehiculo;
import uy.edu.fing.tse.demo2023.persistence.IVehiculosDAO;
import uy.edu.fing.tse.demo2023.util.qualifier.TSE2023DB;

@Stateless
public class VehiculosDAO implements IVehiculosDAO{

	@TSE2023DB
	@Inject
	public EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<VehiculoDTO> obtenerVehiculos() {

		Query q = em.createNativeQuery("select * from public.\"Vehiculo\"", Vehiculo.class);
		
		List<Vehiculo> result = q.getResultList();		
		
		ArrayList<VehiculoDTO> res = new ArrayList<VehiculoDTO>();
		
		result.forEach(v -> res.add(new VehiculoDTO(v)));
		
		return res;
	}

	@Override
	public int obtenerCantidadFilas() {
		Query q = em.createNativeQuery("select count(*) from public.\"Vehiculo\"");
		
		int result = ((Number)q.getSingleResult()).intValue();
		
		return result;
	}

	@Override
	public ArrayList<VehiculoDTO> obtenerVehiculosPaginados(int pageSize, int currentPage) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select * from public.\"Vehiculo\" ");
		sb.append(" limit ");
		sb.append(pageSize);
		sb.append(" offset ");
		sb.append(pageSize * (currentPage - 1));
		
		
		Query q = em.createNativeQuery(sb.toString(), Vehiculo.class);
		List<Vehiculo> result = q.getResultList();

		ArrayList<VehiculoDTO> res = new ArrayList<VehiculoDTO>();
		
		result.forEach(v -> res.add(new VehiculoDTO(v)));
		
		return res;
	}

	@Override
	public VehiculoDTO obtenerVehiculo(int id) throws NoResultException {
		Query q = em.createNativeQuery("select * from public.\"Vehiculo\" where id = " + id, Vehiculo.class);
		
		try {
		
			Vehiculo result = (Vehiculo) q.getSingleResult();
			return new VehiculoDTO(result);
			
		} catch (NoResultException e) {
			throw e;
		}
	}

}

package uy.edu.fing.tse.demo2023.services;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uy.edu.fing.tse.demo2023.dto.VehiculoDTO;
import uy.edu.fing.tse.demo2023.service.IVehiculosService;

@RequestScoped
@Path("/api")
@Produces("application/json")
@Consumes("application/json")
public class RestAPI {

	@EJB
	IVehiculosService service;

	@GET
	@Path("/vehiculos")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getVehiculosPaginados(@QueryParam("pageSize") int pageSize, @QueryParam("currentPage") int currentPage) {	
		
		if (pageSize == 0) {
			return Response.status(Response.Status.OK).entity(service.obtenerVehiculos()).build();
		}
	
		return Response.status(Response.Status.OK).entity(service.obtenerVehiculosPaginados(pageSize, currentPage)).build();
		
	}
	
	@GET
	@Path("/vehiculos/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIniciativaById(@PathParam("id") int id) {
		
		try {
			VehiculoDTO vehiculo = service.obtenerVehiculo(id);
			return Response.status(Response.Status.OK).entity(vehiculo).build();
			
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} 
	}
	
	/*@PUT
	@Path("/iniciativas/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateIniciativa(@PathParam("id") int id, 
			@FormParam("nombre") String nombre,
			@FormParam("descripcion") String descripcion,
			@FormParam("presupuesto") double presupuesto) {
		
		IniciativaDTO iniciativa = new IniciativaDTO(id, nombre, descripcion, presupuesto);
		int updated = service.actualizarIniciativa(iniciativa);
		
		if (updated == 1) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(404).build();
		}
	}*/

}

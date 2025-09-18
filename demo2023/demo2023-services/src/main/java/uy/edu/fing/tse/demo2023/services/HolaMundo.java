package uy.edu.fing.tse.demo2023.services;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/HolaMundo")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class HolaMundo {

	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response ping() {
		
		return Response.status(200).entity("OK").build();
		
		
	}
	
}

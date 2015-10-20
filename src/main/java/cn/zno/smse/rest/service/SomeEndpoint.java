package cn.zno.smse.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/some")
public interface SomeEndpoint {
	
	@GET
	@Path("/ping/{input}")
	@Produces({ MediaType.TEXT_PLAIN})
	@Consumes({ MediaType.TEXT_PLAIN})
	public String ping(@PathParam("input") String input);
}

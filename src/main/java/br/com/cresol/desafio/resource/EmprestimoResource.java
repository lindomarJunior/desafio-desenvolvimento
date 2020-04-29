package br.com.cresol.desafio.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.cresol.desafio.dto.ErrosResponse;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.EmprestimoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

/**
 * @author evandro
 *
 */
@Api
@Path("/emprestimo")
@Produces({"application/json", "application/xml"})
public class EmprestimoResource {
	
	static EmprestimoService emprestimoService = new EmprestimoService();
	
	@POST
	@Path("/simular")
	public Response simular(@ApiParam SimularEmprestimoPayload payload) { 
		ErrosResponse errosResponse = emprestimoService.validarCampos(payload);
		
		if(!errosResponse.getErros().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity(errosResponse).build();
		}
		return Response.ok().entity(emprestimoService.simular(payload)).build();
	}
	
	@GET
	@Path("/{numeroContrato}")
	public Response consultar(@PathParam("numeroContrato") String numeroContrato) {
		try {
			return Response.ok().entity(emprestimoService.consultar(numeroContrato)).build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/{numeroContrato}")
	public Response excluir(@PathParam("numeroContrato") String numeroContrato) {
		try {
			emprestimoService.excluir(numeroContrato);
			return Response.ok().build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

}

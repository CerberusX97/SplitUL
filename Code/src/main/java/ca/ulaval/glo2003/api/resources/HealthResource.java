package ca.ulaval.glo2003.api.resources;

import ca.ulaval.glo2003.api.responses.HealthResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/health")
public class HealthResource {
	private HealthResponse healthResponse;
	public HealthResource(HealthResponse healthResponse) {
		this.healthResponse = healthResponse;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response health() {
		return Response.ok(healthResponse.getStatus()).build();
	}
}

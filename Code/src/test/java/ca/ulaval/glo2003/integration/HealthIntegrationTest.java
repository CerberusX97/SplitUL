package ca.ulaval.glo2003.integration;

import ca.ulaval.glo2003.application.exceptionMappers.HealthExceptionMapper;
import ca.ulaval.glo2003.api.resources.HealthResource;
import ca.ulaval.glo2003.api.responses.HealthResponse;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

public class HealthIntegrationTest extends JerseyTest {
	private HealthResponse healthResponse;

	@Override
	protected Application configure() {
		healthResponse = Mockito.mock(HealthResponse.class);
		return new ResourceConfig().register(new HealthResource(healthResponse)).register(new HealthExceptionMapper());
	}

	@Test
	public void whenStatusIsOk_ThenStatusIs200() {
		when(healthResponse.getStatus()).thenReturn("ok");

		Response response = target("/health").request().get();

		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.readEntity(String.class)).contains("ok");
	}

	@Test
	public void whenStatusIsNotOK_ThenStatusIs500() {
		when(healthResponse.getStatus()).thenThrow(new RuntimeException());

		Response response = target("/health").request().get();

		assertThat(response.getStatus()).isEqualTo(500);
	}
}

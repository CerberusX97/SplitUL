package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.resources.GroupResource;
import ca.ulaval.glo2003.api.resources.HealthResource;
import ca.ulaval.glo2003.api.responses.HealthResponse;
import ca.ulaval.glo2003.application.exceptionMappers.ExpenseExceptionMapper;
import ca.ulaval.glo2003.application.exceptionMappers.GroupsExceptionMapper;
import ca.ulaval.glo2003.application.exceptionMappers.MemberExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
	public static final String BASE_URI = "http://0.0.0.0:8080/";

	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().register(new GroupsExceptionMapper())
				.register(new MemberExceptionMapper()).register(new ExpenseExceptionMapper())
				.register(new GroupResource()).register(HealthResource.class).register(HealthResponse.class);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	public static void main(String[] args) {
		startServer();
		System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
	}
}

package ca.ulaval.glo2003.integration;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.api.requests.ExpenseRequest;
import ca.ulaval.glo2003.api.requests.GroupRequest;
import ca.ulaval.glo2003.api.requests.MemberRequest;
import ca.ulaval.glo2003.api.resources.GroupResource;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.application.exceptionMappers.MemberExceptionMapper;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith({MockitoExtension.class})
public class MemberIntegrationTest extends JerseyTest {
	private static HttpServer server;
	private static final String GOOD_NAME = "Les-Colocs";
	private static final String BAD_NAME = "les Colocs";
	private static final String MEMBER_NAME = "MemberName";

	@BeforeAll
	static void startServer() {
		server = Main.startServer();
	}

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new GroupResource()).register(new MemberExceptionMapper());
	}

	@AfterAll
	static void stopServer() {
		server.shutdown();
	}

	private void createGroup(String groupName) {
		GroupRequest request = new GroupRequest(groupName);
		target("/groups").request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}

	private Response addMember(String member) {
		MemberRequest request = new MemberRequest(member);
		return target("/groups/" + GOOD_NAME + "/members").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}

	private void addExpense(String description, double amount, String purchaseDate, String paidBy, String split) {
		ExpenseRequest request = new ExpenseRequest(description, amount, purchaseDate, paidBy, split);

	}

	private void deleteGroup() {
		target("/groups/" + GOOD_NAME).request(MediaType.APPLICATION_JSON).delete();
	}
	@Test
	void givenAGroup_whenCreateMember_ThenReturnTheGroupAndItsMember() {
		createGroup(GOOD_NAME);

		Response response = addMember(MEMBER_NAME);

		assertThat(response.getStatus()).isEqualTo(201);
		assertThat(response.getLocation().getPath()).isEqualTo("/groups/" + GOOD_NAME + "/members/" + MEMBER_NAME);
		assertThat(response.getHeaderString("Content-Length")).isAnyOf(null, "0");
		assertThat(response.readEntity(String.class)).isEmpty();
	}

	@Test
	void givenAGroupAndInvalidMember_whenCreateMember_ThenReturnTheGroup() {
		createGroup(GOOD_NAME);

		Response response = addMember(BAD_NAME);
		String responseBody = response.readEntity(String.class);

		assertThat(response.getStatus()).isEqualTo(400);
		assertThat(response.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseBody).contains("\"error\": \"INVALID_PARAMETER\"");
		assertThat(responseBody).contains("\"description\": \"Le nom du membre contient un ou des espaces\"");
	}

	@Test
	void givenAGroupExistingMember_whenCreateMember_ThenExpect409Conflict() {
		createGroup(GOOD_NAME);
		addMember(MEMBER_NAME);

		Response response = addMember(MEMBER_NAME);
		String responseBody = response.readEntity(String.class);

		assertThat(response.getStatus()).isEqualTo(409);
		assertThat(response.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseBody).contains("\"error\": \"CONFLICTING_PARAMETER\"");
		assertThat(responseBody)
				.contains(String.format("\"description\": \"Le membre %s est déjà dans le groupe\"", MEMBER_NAME));
	}

}

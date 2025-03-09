package ca.ulaval.glo2003.integration;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.api.requests.GroupRequest;
import ca.ulaval.glo2003.api.requests.MemberRequest;
import ca.ulaval.glo2003.api.resources.GroupResource;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.application.exceptionMappers.GroupsExceptionMapper;
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
public class GroupIntegrationTest extends JerseyTest {
	private static HttpServer server;
	private static final String GOOD_NAME = "Les-Colocs";
	private static final String BAD_NAME = "les Colocs";
	private static final List<MemberDto> members = new ArrayList<>();
	private static final String MEMBER_NAME = "MemberName";
	private static final List<String> MEMBERS = new ArrayList<>();

	@BeforeAll
	static void startServer() {
		server = Main.startServer();
	}

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new GroupResource()).register(new GroupsExceptionMapper());
	}

	@AfterAll
	static void stopServer() {
		server.shutdown();
	}

	private Response createGroup(String groupName) {
		GroupRequest request = new GroupRequest(groupName);
		return target("/groups").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}

	private Response deleteGroup() {
		return target("/groups/" + GOOD_NAME).request(MediaType.APPLICATION_JSON).delete();
	}

	private void addMember(String member) {
		MemberRequest request = new MemberRequest(member);
		target("/groups/" + GOOD_NAME + "/members").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}

	@Test
	void givenGoodGroup_whenCreateGroup_ThenExpect201() {
		Response response = createGroup(GOOD_NAME);

		assertThat(response.getStatus()).isEqualTo(201);
		assertThat(response.getLocation().getPath()).isEqualTo("/groups/" + GOOD_NAME);
		assertThat(response.getHeaderString("Content-Length")).isAnyOf(null, "0");
		assertThat(response.readEntity(String.class)).isEmpty();
	}

	@Test
	void givenExistingGroup_WhenCreateGroup_ThenExpect409Conflict() {
		createGroup(GOOD_NAME);
		String expectedResponse = String.format(
				"{\"error\": \"CONFLICTING_PARAMETER\"," + " \"description\": \"Le nom du groupe %s existe déjà\"}",
				GOOD_NAME);

		Response responseDuplicate = createGroup(GOOD_NAME);

		assertThat(responseDuplicate.getStatus()).isEqualTo(409);
		assertThat(responseDuplicate.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseDuplicate.readEntity(String.class)).isEqualTo(expectedResponse);

	}

	@Test
	void givenBadGroupName_WhenCreateGroup_ThenExpect400BadRequest() {
		Response response = createGroup(BAD_NAME);

		String responseBody = response.readEntity(String.class);
		String expectedJson = String.format("{\"error\": \"INVALID_PARAMETER\", \"description\": \"%s\"}",
				"Le nom du groupe contient un ou des espaces");

		assertThat(response.getStatus()).isEqualTo(400);
		assertThat(response.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseBody).isEqualTo(expectedJson);
	}

	@Test
	void givenGroup_WhenGroupExist_ThenExpect204NoContent() {
		createGroup(GOOD_NAME);

		Response responseDelete = deleteGroup();

		assertThat(responseDelete.getStatus()).isEqualTo(204);
		assertThat(responseDelete.readEntity(String.class)).isEmpty();
	}

	@Test
	void givenNoGroups_WhenDeleteGroup_ThenExpect404NotFound() {
		Response responseDelete = deleteGroup();
		String expectedJson = String
				.format("{\"error\": \"ENTITY_NOT_FOUND\", \"description\": \"Le groupe %s n'existe pas\"}", GOOD_NAME);

		assertThat(responseDelete.getStatus()).isEqualTo(404);
		assertThat(responseDelete.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseDelete.readEntity(String.class)).isEqualTo(expectedJson);
	}

	@Test
	void givenAGroup_WhenGetGroups_ThenReturnAllGroups() {
		createGroup(GOOD_NAME);

		Response responseGet = target("/groups").request(MediaType.APPLICATION_JSON).get();
		String expectedJson = String.format("[{\"name\":\"%s\",\"members\":[]}]", GOOD_NAME);

		assertThat(responseGet.getStatus()).isEqualTo(200);
		assertThat(responseGet.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseGet.readEntity(String.class)).isEqualTo(expectedJson);
	}

	@Test
	void givenNoGroups_WhenGetGroups_ThenReturnEmptyList() {
		Response responseGet = target("/groups").request(MediaType.APPLICATION_JSON).get();
		String expectedJson = String.format("[]");

		assertThat(responseGet.getStatus()).isEqualTo(200);
		assertThat(responseGet.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseGet.readEntity(String.class)).isEqualTo(expectedJson);
	}

	@Test
	void givenAGroup_whenGetGroup_ThenReturnTheGroup() {
		createGroup(GOOD_NAME);

		Response responseGet = target("/groups/" + GOOD_NAME).request(MediaType.APPLICATION_JSON).get();
		String expectedJson = String.format("{\"name\":\"%s\",\"members\":[]}", GOOD_NAME);

		assertThat(responseGet.getStatus()).isEqualTo(200);
		assertThat(responseGet.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseGet.readEntity(String.class)).isEqualTo(expectedJson);
	}

	@Test
	void givenInexistantGroup_whenGetGroup_ThenExpect404NotFound() {
		Response responseGet = target("/groups/" + GOOD_NAME).request(MediaType.APPLICATION_JSON).get();
		String expectedJson = String
				.format("{\"error\": \"ENTITY_NOT_FOUND\", \"description\": \"Le groupe %s n'existe pas\"}", GOOD_NAME);

		assertThat(responseGet.getStatus()).isEqualTo(404);
		assertThat(responseGet.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseGet.readEntity(String.class)).isEqualTo(expectedJson);
	}

	@Test
	void givenAGroupWithAMember_whenGetGroup_ThenReturnTheGroupAndItsMember() {
		createGroup(GOOD_NAME);
		addMember(MEMBER_NAME);
		String expectedJson = String.format("{\"name\":\"%s\",\"members\":[\"%s\"]}", GOOD_NAME, MEMBER_NAME);

		Response responseGet = target("/groups/" + GOOD_NAME).request(MediaType.APPLICATION_JSON).get();

		assertThat(responseGet.getStatus()).isEqualTo(200);
		assertThat(responseGet.getHeaderString("Content-Type")).isEqualTo("application/json");
		assertThat(responseGet.readEntity(String.class)).isEqualTo(expectedJson);
	}
}

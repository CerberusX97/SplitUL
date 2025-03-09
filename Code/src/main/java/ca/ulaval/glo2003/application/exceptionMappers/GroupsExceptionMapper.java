package ca.ulaval.glo2003.application.exceptionMappers;

import ca.ulaval.glo2003.entities.exceptions.GroupAlreadyExistsException;
import ca.ulaval.glo2003.entities.exceptions.GroupException;
import ca.ulaval.glo2003.entities.exceptions.GroupNotFoundException;
import ca.ulaval.glo2003.entities.exceptions.InvalidGroupNameException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GroupsExceptionMapper implements ExceptionMapper<GroupException> {
	@Override
	public Response toResponse(GroupException exception) {
		String errorMessage;

		if (exception instanceof InvalidGroupNameException) {
			errorMessage = String.format("{\"error\": \"INVALID_PARAMETER\", \"description\": \"%s\"}",
					exception.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}

		if (exception instanceof GroupAlreadyExistsException) {
			String groupName = ((GroupAlreadyExistsException) exception).getGroupName();
			errorMessage = String.format(
					"{\"error\": \"CONFLICTING_PARAMETER\", \"description\": \"Le nom du groupe %s existe déjà\"}",
					groupName);
			return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
		}
		if (exception instanceof GroupNotFoundException) {
			String groupName = ((GroupNotFoundException) exception).getGroupName();
			errorMessage = String.format(
					"{\"error\": \"ENTITY_NOT_FOUND\", \"description\": \"Le groupe %s n'existe pas\"}", groupName);
			return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
		}
		return toResponseDefault();
	}

	private Response toResponseDefault() {
		String errorMessage = """
				{
				  "error": "INTERNAL_SERVER_ERROR",
				  "description": "Une erreur interne est survenue"
				}
				""";
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}
}
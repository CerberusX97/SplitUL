package ca.ulaval.glo2003.application.exceptionMappers;

import ca.ulaval.glo2003.entities.exceptions.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MemberExceptionMapper implements ExceptionMapper<MemberException> {

	@Override
	public Response toResponse(MemberException exception) {
		if (exception instanceof InvalidMemberNameException) {
			return toResponse((InvalidMemberNameException) exception);
		}
		if (exception instanceof MemberAlreadyExistsException) {
			return toResponse((MemberAlreadyExistsException) exception);
		}
		if (exception instanceof MemberNotInGroupException) {
			return toResponse((MemberNotInGroupException) exception);
		}
		if (exception instanceof MemberNotFoundException) {
			return toResponse((MemberNotFoundException) exception);
		}

		return toResponseDefault();
	}

	private Response toResponse(InvalidMemberNameException exception) {
		String errorMessage = """
				{
				  "error": "INVALID_PARAMETER",
				  "description": "Le nom du membre contient un ou des espaces"
				}
				""";
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

	private Response toResponse(MemberAlreadyExistsException exception) {
		String errorMessage = String.format("""
				{
				  "error": "CONFLICTING_PARAMETER",
				  "description": "Le membre %s est déjà dans le groupe"
				}
				""", exception.getMemberName());
		return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
	}

	private Response toResponse(MemberNotInGroupException exception) {
		String errorMessage = """
				{
				  "error": "FORBIDDEN",
				  "description": "Vous n'etes pas membre du groupe"
				}
				""";
		return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
	}

	private Response toResponse(MemberNotFoundException exception) {
		String errorMessage = String.format(
				"{\"error\": \"ENTITY_NOT_FOUND\", \"description\": \"Le membre %s n'existe pas dans ce groupe\"}",
				exception.getMemberName());

		return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
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

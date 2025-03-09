package ca.ulaval.glo2003.application.exceptionMappers;

import ca.ulaval.glo2003.entities.exceptions.ExpenseAmountIsNegatifException;
import ca.ulaval.glo2003.entities.exceptions.ExpenseException;
import ca.ulaval.glo2003.entities.exceptions.ExpenseFutureDateException;
import ca.ulaval.glo2003.entities.exceptions.ExpenseInvalidDateFormat;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExpenseExceptionMapper implements ExceptionMapper<ExpenseException> {

	@Override
	public Response toResponse(ExpenseException e) {
		String errorMessage;
		if (e instanceof ExpenseFutureDateException) {
			errorMessage = String.format(
					"{\"error\": \"INVALID_PARAMETER\", \"description\": \"La date d'achat est apres la date courante\"}");

			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		if (e instanceof ExpenseInvalidDateFormat) {
			errorMessage = String.format(
					"{\"error\": \"INVALID_PARAMETER\", \"description\": \"La date n'a pas le bon format. Utilisez le format yyyy-MM-dd\"}");
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		if (e instanceof ExpenseAmountIsNegatifException) {
			errorMessage = String
					.format("{\"error\": \"INVALID_PARAMETER\", \"description\": \"Le montant est negatif\"}");
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
		}
		errorMessage = """
				{
				  "error": "INTERNAL_SERVER_ERROR",
				  "description": "Une erreur interne est survenue"
				}
				""";
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}
}

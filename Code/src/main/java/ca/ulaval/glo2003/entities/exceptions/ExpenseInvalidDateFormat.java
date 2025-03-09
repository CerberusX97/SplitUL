package ca.ulaval.glo2003.entities.exceptions;

public class ExpenseInvalidDateFormat extends ExpenseException {
	public ExpenseInvalidDateFormat() {
		super("La date n'a pas le bon format. Utilisez le format yyyy-MM-dd");
	}
}

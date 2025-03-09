package ca.ulaval.glo2003.entities.exceptions;

public class ExpenseFutureDateException extends ExpenseException {
	public ExpenseFutureDateException() {
		super("La date d'achat est apres la date courante");
	}
}

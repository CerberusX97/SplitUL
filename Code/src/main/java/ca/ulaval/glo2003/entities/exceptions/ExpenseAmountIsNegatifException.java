package ca.ulaval.glo2003.entities.exceptions;

public class ExpenseAmountIsNegatifException extends ExpenseException {

	public ExpenseAmountIsNegatifException() {
		super("Le montant est negatif");
	}
}

package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.ExpenseRequest;
import ca.ulaval.glo2003.application.dtos.ExpenseDto;

public class ExpenseAssembler {

	public ExpenseAssembler() {
	}

	public static ExpenseDto fromRequest(ExpenseRequest expenseRequest) {
		return new ExpenseDto(expenseRequest);
	}
}

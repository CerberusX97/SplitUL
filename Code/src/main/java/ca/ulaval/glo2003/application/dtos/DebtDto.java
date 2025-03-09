package ca.ulaval.glo2003.application.dtos;

import ca.ulaval.glo2003.entities.Debt;

public class DebtDto {
	private String memberNameDto;
	private double amountDto;

	public DebtDto(Debt debt) {
		this.memberNameDto = debt.getMemberName();
		this.amountDto = debt.getAmount();
	}

	public String getMemberNameDto() {
		return memberNameDto;
	}

	public double getAmountDto() {
		return amountDto;
	}
}

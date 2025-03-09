package ca.ulaval.glo2003.application.dtos;

import ca.ulaval.glo2003.api.requests.ExpenseRequest;
import ca.ulaval.glo2003.entities.Split;

public class ExpenseDto {

	private String description;
	private double amount;
	private String purchaseDateString;
	private String paidBy;
	private Split split;

	public ExpenseDto(ExpenseRequest expenseRequest) {
		this.description = expenseRequest.getDescription();
		this.amount = expenseRequest.getAmount();
		this.purchaseDateString = expenseRequest.getPurchaseDate();
		this.paidBy = expenseRequest.getPaidBy();
		this.split = Split.EQUALLY;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public String getPurchaseDateString() {
		return purchaseDateString;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public Split getSplit() {
		return split;
	}

}

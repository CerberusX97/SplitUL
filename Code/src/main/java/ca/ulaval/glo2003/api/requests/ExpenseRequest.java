package ca.ulaval.glo2003.api.requests;

public class ExpenseRequest {
	private String description;
	private double amount;
	private String purchaseDate;
	private String paidBy;
	private String split;

	public ExpenseRequest() {
	}

	public ExpenseRequest(String description, double amount, String purchaseDate, String paidBy, String split) {
		this.description = description;
		this.amount = amount;
		this.purchaseDate = purchaseDate;
		this.paidBy = paidBy;
		this.split = split;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public String getSplit() {
		return split;
	}
}

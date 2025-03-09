package ca.ulaval.glo2003.entities;

public class Debt {
	private String memberName;
	private double amount;

	public Debt(String memberName, double amount) {
		this.memberName = memberName;
		this.amount = amount;
	}
	public String getMemberName() {
		return memberName;
	}

	public double getAmount() {
		return amount;
	}

	public void increaseAmount(double share) {
		amount += share;
	}
}

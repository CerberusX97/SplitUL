package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.ExpenseDto;

import java.util.ArrayList;
import java.util.UUID;

public class Group {

	private String groupName;
	private String groupID;
	private ArrayList<Member> groupMembers = new ArrayList<Member>();
	private ArrayList<Expense> expenses = new ArrayList<Expense>();

	public Group(String name) {
		this.groupName = name;
		this.groupID = UUID.randomUUID().toString();

	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupID() {
		return groupID;
	}

	public ArrayList<Member> getGroupMembers() {
		return groupMembers;
	}

	public ArrayList<Expense> getExpenses() {
		return expenses;
	}

	public void addMember(Member member) {
		this.groupMembers.add(member);
	}

	public UUID addExpense(ExpenseDto expensedto, Member member) {

		Expense expense = new Expense(expensedto.getDescription(), expensedto.getAmount(),
				expensedto.getPurchaseDateString(), member, expensedto.getSplit());

		expenses.add(expense);
		updateDebts(member.getMemberName(), expense.getAmount());
		return expense.getId();
	}
	public void updateDebts(String memberName, double amount) {
		double share = amount / groupMembers.size();
		share = Math.round(share * 100.0) / 100.0;
		for (Member member : groupMembers) {
			if (!member.getMemberName().equals(memberName)) {
				member.updateMyDebt(memberName, share);
			}
		}
	}

}

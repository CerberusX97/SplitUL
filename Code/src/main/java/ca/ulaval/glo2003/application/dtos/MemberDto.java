package ca.ulaval.glo2003.application.dtos;

import ca.ulaval.glo2003.entities.Debt;
import ca.ulaval.glo2003.entities.Member;

import java.util.ArrayList;

public class MemberDto {
	private String memberName;
	private ArrayList<DebtDto> debts = new ArrayList<>();

	public MemberDto(String memberName) {
		this.memberName = memberName;
	}

	public MemberDto(Member member) {
		this.memberName = member.getMemberName();
		setDebts(member.getMemberDebts());
	}

	private void setDebts(ArrayList<Debt> debts) {
		for (Debt debt : debts) {
			this.debts.add(new DebtDto(debt));
		}
	}

	public ArrayList<DebtDto> getDebts() {
		return debts;
	}

	public String getMemberName() {
		return memberName;
	}
}

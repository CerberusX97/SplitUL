package ca.ulaval.glo2003.entities;

import java.util.ArrayList;
import java.util.UUID;

public class Member {

	private String memberName;
	private UUID uuid;
	private ArrayList<Debt> memberDebts = new ArrayList<Debt>();

	public Member(String name) {

		this.memberName = name;
		this.uuid = UUID.randomUUID();
	}
	public String getMemberName() {
		return memberName;
	}

	public UUID getUuid() {
		return uuid;
	}
	public ArrayList<Debt> getMemberDebts() {
		return memberDebts;
	}

	public void updateMyDebt(String memberName, double share) {
		Debt newDebt = new Debt(memberName, share);
		memberDebts.stream().filter(debt -> debt.getMemberName().equals(memberName)).findFirst().ifPresentOrElse(
				debt -> newDebt.increaseAmount(share), // Update if found
				() -> memberDebts.add(new Debt(memberName, share)) // Add new if not found
		);
	}
}

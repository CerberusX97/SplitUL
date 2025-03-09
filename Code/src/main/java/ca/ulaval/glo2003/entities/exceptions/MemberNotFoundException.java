package ca.ulaval.glo2003.entities.exceptions;

public class MemberNotFoundException extends MemberException {
	private final String memberName;

	public MemberNotFoundException(String memberName) {
		super("Le membre " + memberName + " n'existe pas");
		this.memberName = memberName;
	}

	public String getMemberName() {
		return memberName;
	}
}
package ca.ulaval.glo2003.entities.exceptions;

public class MemberAlreadyExistsException extends MemberException {
	private final String memberName;

	public MemberAlreadyExistsException(String memberName) {
		super("Le nom du membre " + memberName + " existe déjà");
		this.memberName = memberName;
	}

	public String getMemberName() {
		return memberName;
	}
}
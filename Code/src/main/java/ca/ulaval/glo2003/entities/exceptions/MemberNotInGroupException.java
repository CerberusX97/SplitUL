package ca.ulaval.glo2003.entities.exceptions;

public class MemberNotInGroupException extends MemberException {
	public MemberNotInGroupException(String memberName, String groupName) {
		super(String.format("Le membre %s n'est pas dans le groupe %s", memberName, groupName));
	}
}

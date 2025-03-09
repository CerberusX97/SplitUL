package ca.ulaval.glo2003.entities.exceptions;

public class GroupNotFoundException extends GroupException {
	private final String groupName;

	public GroupNotFoundException(String groupName) {
		super("Le groupe " + groupName + " n'existe pas ");
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
}

package ca.ulaval.glo2003.entities.exceptions;

public class GroupAlreadyExistsException extends GroupException {
	private final String groupName;

	public GroupAlreadyExistsException(String groupName) {
		super("Le nom du groupe " + groupName + " existe déjà");
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}
}

package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.GroupDto;

public class GroupFactory {

	public Group createGroup(GroupDto groupdto) {
		return new Group(groupdto.getNameGroupDto());
	}

}

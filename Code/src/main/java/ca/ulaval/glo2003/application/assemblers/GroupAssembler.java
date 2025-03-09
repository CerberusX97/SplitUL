package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.GroupRequest;
import ca.ulaval.glo2003.application.dtos.GroupDto;
import ca.ulaval.glo2003.entities.Group;

public class GroupAssembler {

	public GroupAssembler() {
	}

	public static GroupDto todto(Group group) {
		return new GroupDto(group);
	}

	public static GroupDto fromRequest(GroupRequest groupRequest) {
		return new GroupDto(groupRequest.getName());
	}
}

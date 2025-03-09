package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.application.dtos.GroupDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"name", "members"})
public class GroupResponse {

	private String name;
	private ArrayList<String> members;

	public GroupResponse() {
	}

	public GroupResponse(GroupDto groupDto) {
		this.name = groupDto.getNameGroupDto();
		this.members = groupDto.getGroupMembersNameDto();
	}

	public String getName() {
		return name;
	}

	public List<String> getMembers() {
		return members;
	}

	public List<GroupResponse> groupsResponses(List<GroupDto> groupDtos) {
		List<GroupResponse> responses = new ArrayList<>();
		responses.addAll(groupDtos.stream().map(GroupResponse::new).toList());
		return responses;
	}
}
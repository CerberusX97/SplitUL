package ca.ulaval.glo2003.application.dtos;

import ca.ulaval.glo2003.entities.Group;
import ca.ulaval.glo2003.entities.Member;

import java.util.ArrayList;
import java.util.List;

public class GroupDto {
	private String nameGroupDto;
	private ArrayList<MemberDto> members = new ArrayList<>();

	public GroupDto(Group group) {
		this.nameGroupDto = group.getGroupName();
		setMembers(group.getGroupMembers());
	}

	private void setMembers(ArrayList<Member> members) {
		for (Member member : members) {
			this.members.add(new MemberDto(member));
		}
	}
	public GroupDto(String name) {
		this.nameGroupDto = name;
		this.members = new ArrayList<>();
	}

	public String getNameGroupDto() {
		return nameGroupDto;
	}

	public ArrayList<String> getGroupMembersNameDto() {
		ArrayList<String> members = new ArrayList<>();
		for (MemberDto memberDto : this.members) {
			members.add(memberDto.getMemberName());
		}
		return members;
	}

}

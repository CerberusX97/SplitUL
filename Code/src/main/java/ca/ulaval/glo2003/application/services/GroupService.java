package ca.ulaval.glo2003.application.services;

import ca.ulaval.glo2003.application.assemblers.GroupAssembler;
import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import ca.ulaval.glo2003.application.dtos.GroupDto;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.entities.Group;
import ca.ulaval.glo2003.entities.GroupFactory;
import ca.ulaval.glo2003.entities.Member;
import ca.ulaval.glo2003.entities.MemberFactory;
import ca.ulaval.glo2003.entities.exceptions.GroupNotFoundException;
import ca.ulaval.glo2003.entities.exceptions.MemberNotInGroupException;
import ca.ulaval.glo2003.infrastructure.GroupRepositoryMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupService {
	private GroupRepositoryMemory groupRepository = new GroupRepositoryMemory();
	private GroupFactory groupFactory = new GroupFactory();
	private MemberFactory memberFactory = new MemberFactory();

	public GroupService() {
	}

	public void validateNameGroup(String nameGroup) {
		groupRepository.validateNameGroup(nameGroup);
	}

	public void CreateGroup(GroupDto groupdto) {
		this.validateNameGroup(groupdto.getNameGroupDto());
		Group group = groupFactory.createGroup(groupdto);
		groupRepository.addGroup(group);
	}

	public void deleteGroup(GroupDto groupdto) {
		groupRepository.deleteGroup(groupdto.getNameGroupDto());
	}

	public GroupDto getGroup(String nameGroup) throws GroupNotFoundException {
		Group group = groupRepository.getGroup(nameGroup);
		return GroupAssembler.todto(group);
	}

	public List<GroupDto> getAllGroups() {
		List<Group> groups = groupRepository.getALlGroups();
		List<GroupDto> groupsdto = new ArrayList<>();

		for (Group group : groups) {
			groupsdto.add(GroupAssembler.todto(group));
		}
		return groupsdto;
	}

	public void addMember(String nameGroup, MemberDto memberdto) {
		this.validateNameMember(memberdto.getMemberName(), nameGroup);
		Member member = memberFactory.createMember(memberdto);
		groupRepository.addMemberToGroup(nameGroup, member);
	}

	public void validateNameMember(String nameGroup, String nameMember) {
		groupRepository.validateNameMember(nameGroup, nameMember);
	}

	public boolean isMemberInGroup(Group group, String memberName) {
		return group.getGroupMembers().stream().anyMatch(member -> member.getMemberName().equals(memberName));
	}

	public List<MemberDto> getAllMembersOfGroup(String groupName, String requestingMember) {
		Group group = groupRepository.getGroup(groupName);

		if (!isMemberInGroup(group, requestingMember)) {
			throw new MemberNotInGroupException(requestingMember, groupName);
		}

		// MemberDto::new instead of member -> new MemberDto(member) to ensure clean
		// code
		return group.getGroupMembers().stream().map(MemberDto::new).toList();
	}

	public UUID addExpense(GroupDto groupdto, ExpenseDto expensedto) {

		Group group = groupRepository.getGroup(groupdto.getNameGroupDto());
		return groupRepository.addExpense(group, expensedto);

	}

}
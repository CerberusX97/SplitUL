package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import ca.ulaval.glo2003.entities.Group;
import ca.ulaval.glo2003.entities.Member;
import ca.ulaval.glo2003.entities.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupRepositoryMemory {
	private List<Group> groups;

	public GroupRepositoryMemory() {
		groups = new ArrayList<>();
	}

	public Group getGroup(String name) {
		if (!groupExists(name)) {
			throw new GroupNotFoundException(name);
		}

		return groups.stream().filter(group -> group.getGroupName().equals(name)).findFirst().orElse(null);
	}

	public boolean groupExists(String name) {
		return groups.stream().anyMatch(group -> group.getGroupName().equals(name));
	}

	public void validateNameGroup(String name) {
		if (name.contains(" ")) {
			throw new InvalidGroupNameException("Le nom du groupe contient un ou des espaces");
		}
		if (name.isEmpty()) {
			throw new InvalidGroupNameException("Le nom du groupe ne peut pas être vide");
		}
		for (Group group : groups) {
			if (group.getGroupName().equals(name)) {
				throw new GroupAlreadyExistsException(name);
			}
		}
	}

	public void addGroup(Group group) {
		groups.add(group);

	}

	public List<Group> getALlGroups() {
		return groups;
	}

	public void deleteGroup(String name) {

		boolean isRemoved = groups.removeIf(group -> group.getGroupName().equals(name));
		if (!isRemoved) {
			throw new GroupNotFoundException(name);
		}

	}
	public Member getMemberFromGroup(String name, Group group) {
		return group.getGroupMembers().stream().filter(element -> element.getMemberName().equals(name)).findFirst()
				.orElse(null);
	}

	public UUID addExpense(Group group, ExpenseDto expensedto) {

		Member member = getMemberFromGroup(expensedto.getPaidBy(), group);
		validateExpense(member, expensedto);
		return group.addExpense(expensedto, member);
	}

	public void validateExpense(Member member, ExpenseDto expensedto) {

		if (member == null) {
			throw new MemberNotFoundException(expensedto.getPaidBy());
		}
		if (expensedto.getAmount() < 0) {
			throw new ExpenseAmountIsNegatifException();
		}

	}
	public void validateNameMember(String nameMember, String groupName) {
		if (nameMember.contains(" ")) {
			throw new InvalidMemberNameException("Le nom du membre contient un ou des espaces");
		}
		Group group = this.getGroup(groupName);
		for (Member member : group.getGroupMembers()) {
			if (member.getMemberName().equals(nameMember)) {
				throw new MemberAlreadyExistsException(nameMember);
			}
		}
	}

	public void addMemberToGroup(String groupName, Member member) {
		Group group = getGroup(groupName);
		group.addMember(member);
	}

}
package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import ca.ulaval.glo2003.entities.Expense;
import ca.ulaval.glo2003.entities.Group;
import ca.ulaval.glo2003.entities.Member;
import ca.ulaval.glo2003.entities.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupRepositoryMemoryTest {
	private final String A_NAME = "les-colocs";
	private final String B_NAME = "les-colocs2";
	private final String INVALID_WITH_SPACES = "les colocs";
	private final String INVALID_EMPTY_NAME = "";
	private final String INVALID_MEMBER_NAME = "Jean Pierre";
	private final String VALID_MEMBER_NAME = "JeanPierre";

	@Mock
	Group group;
	@Mock
	Group group2;
	@Mock
	Member member;
	@Mock
	Expense expense;
	@Mock
	ExpenseDto expensedto;

	@InjectMocks
	GroupRepositoryMemory groupRepositoryMemory;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void givenGroup_WhenAddGroup_ThenGroupsAddedToRepository() {
		groupRepositoryMemory.addGroup(group);

		assertThat(groupRepositoryMemory.getALlGroups().size()).isEqualTo(1);
	}

	@Test
	void given2Group_WhenAddGroups_Then2GroupsAddedToRepository() {
		groupRepositoryMemory.addGroup(group);
		groupRepositoryMemory.addGroup(group2);

		assertThat(groupRepositoryMemory.getALlGroups().size()).isEqualTo(2);
	}

	@Test
	void givenEmptyRepository_WhenDeleteGroup_ThenThrowGroupNotFoundException() {
		when(group.getGroupName()).thenReturn(A_NAME);

		assertThrows(GroupNotFoundException.class, () -> groupRepositoryMemory.deleteGroup(group.getGroupName()));
	}

	@Test
	void givenGroup_WhenDeleteGroup_ThenGroupRemovedFromRepository() {
		when(group.getGroupName()).thenReturn(A_NAME);
		groupRepositoryMemory.addGroup(group);

		groupRepositoryMemory.deleteGroup(group.getGroupName());

		assertThat(groupRepositoryMemory.getALlGroups().size()).isEqualTo(0);
	}

	@Test
	void givenMultipleGroups_WhenDeleteGroup_ThenGroupRemovedFromRepository() {
		groupRepositoryMemory.addGroup(group);
		groupRepositoryMemory.addGroup(group2);
		when(group.getGroupName()).thenReturn(A_NAME);
		when(group2.getGroupName()).thenReturn(B_NAME);

		groupRepositoryMemory.deleteGroup(group.getGroupName());

		assertThrows(GroupNotFoundException.class, () -> groupRepositoryMemory.getGroup(group.getGroupName()));
		assertDoesNotThrow(() -> groupRepositoryMemory.getGroup(group2.getGroupName()));
	}

	@Test
	void givenValidName_WhenValidateName_ThenNothingHappened() {
		assertDoesNotThrow(() -> groupRepositoryMemory.validateNameGroup(A_NAME));
	}

	@Test
	void givenEmptyName_WhenValidateName_ThenThrowInvalidGroupNameException() {
		assertThrows(InvalidGroupNameException.class,
				() -> groupRepositoryMemory.validateNameGroup(INVALID_EMPTY_NAME));
	}

	@Test
	void givenInvalidName_WhenValidateName_ThenThrowInvalidGroupNameException() {
		assertThrows(InvalidGroupNameException.class,
				() -> groupRepositoryMemory.validateNameGroup(INVALID_WITH_SPACES));
	}

	@Test
	void givenValidNameMember_WhenValidateName_ThenNothingHappened() {
		when(group.getGroupName()).thenReturn(A_NAME);
		groupRepositoryMemory.addGroup(group);

		assertDoesNotThrow(() -> groupRepositoryMemory.validateNameMember(VALID_MEMBER_NAME, group.getGroupName()));
	}

	@Test
	void givenInvalidName_WhenValidateName_ThenThrowInvalidMEmberNameException() {
		when(group.getGroupName()).thenReturn(A_NAME);
		groupRepositoryMemory.addGroup(group);

		assertThrows(InvalidMemberNameException.class,
				() -> groupRepositoryMemory.validateNameMember(INVALID_MEMBER_NAME, group.getGroupName()));
	}

	@Test
	void givenExistingMember_WhenValidateName_ThenThrowMemberAlreadyExistsException() {

		Group group = new Group(A_NAME);

		Member member = new Member(VALID_MEMBER_NAME);
		group.addMember(member);

		groupRepositoryMemory.addGroup(group);

		assertThrows(MemberAlreadyExistsException.class,
				() -> groupRepositoryMemory.validateNameMember(VALID_MEMBER_NAME, A_NAME));

	}

	@Test
	void givenMember_WhenAddMember_ThenMemberAddedToGroup() {
		when(group.getGroupName()).thenReturn(A_NAME);

		groupRepositoryMemory.addGroup(group);

		groupRepositoryMemory.addMemberToGroup(A_NAME, member);

		verify(group).addMember(member);
	}

	@Test
	void givenGroup_WhenGetMemberFromGroup_ThenMemberIsReturned() {
		when(member.getMemberName()).thenReturn(A_NAME);
		group.addMember(member);
		ArrayList<Member> members = new ArrayList<>();
		members.add(member);
		groupRepositoryMemory.addGroup(group);
		when(group.getGroupMembers()).thenReturn(members);

		assertThat(groupRepositoryMemory.getMemberFromGroup(A_NAME, group)).isEqualTo(member);
	}

	@Test
	void givenGroup_WhenGetMemberFromGroupInvalid_ThenReturnNull() {
		when(member.getMemberName()).thenReturn(A_NAME);
		group.addMember(member);
		ArrayList<Member> members = new ArrayList<>();
		members.add(member);
		groupRepositoryMemory.addGroup(group);
		when(group.getGroupMembers()).thenReturn(members);

		assertThat(groupRepositoryMemory.getMemberFromGroup("something-else", group)).isEqualTo(null);
	}

	@Test
	void givenExpensedto_whenAmountIsNegatif_ThenThrowExpenseAmountException() {
		double INVALID_AMOUNT = -10;
		when(expensedto.getAmount()).thenReturn(INVALID_AMOUNT);

		assertThrows(ExpenseAmountIsNegatifException.class,
				() -> groupRepositoryMemory.validateExpense(member, expensedto));

	}

	@Test
	void givenNotExisting_whenAddExpense_ThenThrowMemberNotFoundException() {
		groupRepositoryMemory.addGroup(group);
		when(expensedto.getPaidBy()).thenReturn("something-else");
		assertThrows(MemberNotFoundException.class, () -> groupRepositoryMemory.addExpense(group, expensedto));
	}
}
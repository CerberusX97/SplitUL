package ca.ulaval.glo2003.application.services;

import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import ca.ulaval.glo2003.application.dtos.GroupDto;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.entities.Group;
import ca.ulaval.glo2003.entities.GroupFactory;
import ca.ulaval.glo2003.entities.Member;
import ca.ulaval.glo2003.entities.MemberFactory;
import ca.ulaval.glo2003.entities.exceptions.InvalidGroupNameException;
import ca.ulaval.glo2003.entities.exceptions.InvalidMemberNameException;
import ca.ulaval.glo2003.entities.exceptions.MemberNotInGroupException;
import ca.ulaval.glo2003.infrastructure.GroupRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class GroupServiceTest {

	private final String VALID_NAME = "les-colocs";
	private final String INVALID_NAME = "les colocs";
	private static final UUID FIXED_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

	private final String INVALID_MEMBER_NAME = "Jean Pierre";
	private final String VALID_MEMBER_NAME = "JeanPierre";

	@Mock
	GroupRepositoryMemory groupRepositoryMemory;
	@Mock
	GroupFactory groupFactory;
	@Mock
	Group group;
	@Mock
	GroupDto groupdto;
	@Mock
	ExpenseDto expensedto;
	@Mock
	Member member;
	@Mock
	MemberDto memberdto;
	@Mock
	MemberFactory memberFactory;

	@InjectMocks
	GroupService groupService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void givenValidName_WhenValidateNameGroup_ThenNothingHappens() {
		groupService.validateNameGroup(VALID_NAME);

		verify(groupRepositoryMemory).validateNameGroup(VALID_NAME);
	}

	@Test
	public void givenInvalideName_WhenValidateNameGroup_ThenThrowInvalidGroupNameException() {
		doThrow(InvalidGroupNameException.class).when(groupRepositoryMemory).validateNameGroup(INVALID_NAME);

		assertThrows(InvalidGroupNameException.class, () -> groupService.validateNameGroup(INVALID_NAME));
	}

	@Test
	public void givenGroupDTO_WhenCreateGroup_ThenCallAddGroupFromGroupRepositoryMemory() {
		when(groupdto.getNameGroupDto()).thenReturn(VALID_NAME);
		when(groupFactory.createGroup(groupdto)).thenReturn(group);

		groupService.CreateGroup(groupdto);

		verify(groupRepositoryMemory).addGroup(group);
	}

	@Test
	public void givenGroupDTO_WhenDeleteGroup_ThenCallDeleteGroupFromGroupRepositoryMemory() {
		when(groupdto.getNameGroupDto()).thenReturn(VALID_NAME);

		groupService.deleteGroup(groupdto);

		verify(groupRepositoryMemory).deleteGroup(VALID_NAME);
	}

	@Test
	public void givenValidNameMEmber_WhenValidateNameMember_ThenNothingHappens() {
		groupService.validateNameMember(VALID_NAME, VALID_MEMBER_NAME);

		verify(groupRepositoryMemory).validateNameMember(VALID_NAME, VALID_MEMBER_NAME);
	}

	@Test
	public void givenInvalidNameMEmber_WhenValidateNameMember_ThenThenThrowInvalidMemberNameException() {
		doThrow(InvalidMemberNameException.class).when(groupRepositoryMemory).validateNameMember(VALID_NAME,
				INVALID_MEMBER_NAME);

		assertThrows(InvalidMemberNameException.class,
				() -> groupService.validateNameMember(VALID_NAME, INVALID_MEMBER_NAME));
	}

	@Test
	public void givenMemberDTO_WhenaddMember_ThenCallAddMemberFromGroupRepositoryMemory() {
		when(memberdto.getMemberName()).thenReturn(VALID_MEMBER_NAME);
		when(group.getGroupName()).thenReturn(VALID_NAME);
		when(memberFactory.createMember(memberdto)).thenReturn(member);

		groupService.addMember(group.getGroupName(), memberdto);
		verify(groupRepositoryMemory).addMemberToGroup(VALID_NAME, member);
	}

	@Test
	public void givenMemberNotInGroup_WhenGetAllMembersOfGroup_thenThrowException() {
		String groupName = "les-colocs";
		String requestingMember = "nonExistentMember";

		Member member1 = mock(Member.class);
		Member member2 = mock(Member.class);
		when(member1.getMemberName()).thenReturn("Alice");
		when(member2.getMemberName()).thenReturn("Bob");

		when(group.getGroupMembers()).thenReturn(new ArrayList<>(List.of(member1, member2)));
		when(groupRepositoryMemory.getGroup(groupName)).thenReturn(group);

		assertThrows(MemberNotInGroupException.class,
				() -> groupService.getAllMembersOfGroup(groupName, requestingMember));
	}

	@Test
	public void givenMemberInGroup_WhenGetAllMembersOfGroup_thenReturnMemberDtoList() {
		String groupName = "les-colocs";
		String requestingMember = "Alice";

		Member member1 = mock(Member.class);
		Member member2 = mock(Member.class);
		when(member1.getMemberName()).thenReturn("Alice");
		when(member2.getMemberName()).thenReturn("Bob");

		when(group.getGroupMembers()).thenReturn(new ArrayList<>(List.of(member1, member2)));
		when(groupRepositoryMemory.getGroup(groupName)).thenReturn(group);

		List<MemberDto> result = groupService.getAllMembersOfGroup(groupName, requestingMember);
		assertEquals(2, result.size());
	}

	@Test
	public void givenExpenseDTO_whenAddExpense_thenReturnExpenseUUID() {
		when(groupdto.getNameGroupDto()).thenReturn(VALID_NAME);
		when(groupRepositoryMemory.getGroup(VALID_NAME)).thenReturn(group);
		when(groupRepositoryMemory.addExpense(group, expensedto)).thenReturn(FIXED_UUID);

		UUID result = groupService.addExpense(groupdto, expensedto);

		assertEquals(FIXED_UUID, result);
		verify(groupRepositoryMemory).addExpense(group, expensedto);
	}

}

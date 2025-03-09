package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupTest {
	private String A_NAME = "les-colocs";
	private String MEMBER_1 = "member1";
	private String MEMBER_2 = "member2";
	private final String DESCRIPTION = "je suis une description";

	private final double AMOUNT = 100;
	private final String DATE = "2024-11-11";

	@Mock
	Member member;
	@Mock
	Member member2;
	@Mock
	ExpenseDto expensedto;

	@InjectMocks
	Group groupTest;

	@BeforeEach
	void setUp() {
		groupTest = new Group(A_NAME);
	}

	@Test
	void givenGroup_WhenGetGroupName_ThenReturnsName() {
		assertThat(groupTest.getGroupName()).isEqualTo(A_NAME);
	}

	@Test
	void givenGroup_WhenGetGroupID_ThenReturnsID() {
		assertThat(groupTest.getGroupID()).isInstanceOf(String.class);
		assertThat(groupTest.getGroupID()).isNotEmpty();
	}

	@Test
	void givenMember_WhenAddMember_ThenMembersAddedToGroup() {
		groupTest.addMember(member);

		assertThat(groupTest.getGroupMembers().size()).isEqualTo(1);
	}

	@Test
	void givenMember_WhenUpdateDebt_ThenDebtUpdated() {
		groupTest.addMember(member);
		when(member.getMemberName()).thenReturn(MEMBER_1);
		when(member2.getMemberName()).thenReturn(MEMBER_2);
		groupTest.addMember(member2);

		groupTest.updateDebts(member.getMemberName(), AMOUNT);
		verify(member2).updateMyDebt(MEMBER_1, AMOUNT / 2);

	}
	@Test
	void givenExpense_whenAddExpense_ThenExpensesAddedToGroup() {
		when(expensedto.getDescription()).thenReturn(DESCRIPTION);
		when(expensedto.getAmount()).thenReturn(AMOUNT);
		when(expensedto.getSplit()).thenReturn(Split.EQUALLY);
		when(expensedto.getPurchaseDateString()).thenReturn(DATE);
		when(member.getMemberName()).thenReturn(A_NAME);
		groupTest.addMember(member);

		groupTest.addExpense(expensedto, member);

		assertThat(groupTest.getExpenses().size()).isEqualTo(1);

	}

}
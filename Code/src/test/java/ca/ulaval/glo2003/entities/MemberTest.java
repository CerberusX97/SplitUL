package ca.ulaval.glo2003.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberTest {
	private String A_NAME = "JeanPierre";
	private String B_NAME = "Pascal";
	private Member memberTest;
	private Member memberTest2;
	private final double SHARE = 10;

	@Mock
	Debt debtTest;

	@BeforeEach
	void setUp() {
		memberTest = new Member(A_NAME);
		memberTest2 = new Member(B_NAME);
	}

	@Test
	void givenMember_WhenGetMemberName_ThenReturnsName() {
		assertThat(memberTest.getMemberName()).isEqualTo(A_NAME);
	}

	@Test
	void givenMember_WhenUpdateMyDebt_ThenUpdatesMyDebt() {

		memberTest2.updateMyDebt(memberTest.getMemberName(), SHARE);

		assertThat(memberTest2.getMemberDebts().size()).isEqualTo(1);
	}

	@Test
	void givenMember_WhenUpdateExistingDebt_ThenUpdatesMyDebt() {
		when(debtTest.getMemberName()).thenReturn(A_NAME);
		memberTest2.getMemberDebts().add(debtTest);

		memberTest2.updateMyDebt(memberTest.getMemberName(), SHARE);

		assertThat(memberTest2.getMemberDebts().size()).isEqualTo(1);

	}
}
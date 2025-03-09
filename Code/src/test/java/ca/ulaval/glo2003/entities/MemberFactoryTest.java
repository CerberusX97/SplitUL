package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberFactoryTest {

	private MemberFactory memberFactory;
	private final String VALID_MEMBER_NAME = "JeanPierre";

	@Mock
	private Member member;

	@Mock
	private MemberDto memberdto;

	@BeforeEach
	void setUp() {
		memberFactory = new MemberFactory();
	}

	@Test
	void givenMemberDTO_WhenCreateMember_ThenReturnCreatedMember() {

		when(memberdto.getMemberName()).thenReturn(VALID_MEMBER_NAME);

		Member createdMember = memberFactory.createMember(memberdto);

		assertThat(createdMember).isNotNull();
		assertThat(createdMember.getMemberName()).isEqualTo(VALID_MEMBER_NAME);
	}
}

package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.MemberRequest;
import ca.ulaval.glo2003.application.dtos.MemberDto;
import ca.ulaval.glo2003.entities.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
public class MemberAssemblerTest {
	private MemberAssembler assembler;

	@Mock
	Member member;
	@Mock
	MemberRequest memberRequest;

	@BeforeEach
	public void setUp() {
		assembler = new MemberAssembler();
	}

	@Test
	void whentoDto_returnsMemberDto() {

		assertThat(assembler.toMemberDto(member)).isInstanceOf(MemberDto.class);
	}
	@Test
	void whenFromRequest_returnsGroupDto() {
		assertThat(assembler.fromRequest(memberRequest)).isInstanceOf(MemberDto.class);
	}
}

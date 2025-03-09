package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.GroupRequest;
import ca.ulaval.glo2003.application.dtos.GroupDto;
import ca.ulaval.glo2003.entities.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
public class GroupAssemblerTest {
	private GroupAssembler assembler;

	@Mock
	Group group;
	@Mock
	GroupRequest groupRequest;

	@BeforeEach
	public void setUp() {
		assembler = new GroupAssembler();
	}

	@Test
	void whentoDto_returnsGroupDto() {

		assertThat(assembler.todto(group)).isInstanceOf(GroupDto.class);
	}
	@Test
	void whenFromRequest_returnsGroupDto() {
		assertThat(assembler.fromRequest(groupRequest)).isInstanceOf(GroupDto.class);
	}
}

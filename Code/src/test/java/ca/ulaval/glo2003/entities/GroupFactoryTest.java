package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.application.dtos.GroupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupFactoryTest {
	private final String A_NAME = "les-colocs";

	@Mock
	private Group group;
	@Mock
	private GroupDto groupdto;

	@InjectMocks
	GroupFactory groupFactory;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void givenGroupDTO_WhenCreateGroup_ThenReturnCreatedGroup() {
		when(group.getGroupName()).thenReturn(A_NAME);
		when(groupdto.getNameGroupDto()).thenReturn(A_NAME);

		Group created_group = groupFactory.createGroup(groupdto);

		assertThat(created_group.getGroupName()).isEqualTo(group.getGroupName());
	}
}
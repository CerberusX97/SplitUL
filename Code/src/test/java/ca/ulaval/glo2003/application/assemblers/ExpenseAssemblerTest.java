package ca.ulaval.glo2003.application.assemblers;

import ca.ulaval.glo2003.api.requests.ExpenseRequest;
import ca.ulaval.glo2003.application.dtos.ExpenseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;
@ExtendWith(MockitoExtension.class)
public class ExpenseAssemblerTest {
	private ExpenseAssembler assembler;

	@Mock
	ExpenseRequest expenseRequest;
	@Mock
	ExpenseDto expensedto;

	@BeforeEach
	public void setUp() {
		assembler = new ExpenseAssembler();
	}

	@Test
	void whenfromRequest_returnsExpenseDto() {

		assertThat(assembler.fromRequest(expenseRequest)).isInstanceOf(ExpenseDto.class);
	}
}

package ca.ulaval.glo2003.entities;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class DebtTest {
	private final String memberName = "John-Smith";
	private final double amount = 100.0;
	private final double initialDebtAmount = 0;
	private Debt debt = new Debt(memberName, initialDebtAmount);

	@Test
	public void whenIncreaseDebtAmount_thenAmountIncreased() {
		debt.increaseAmount(amount);
		assertThat(debt.getAmount()).isEqualTo(amount);
	}
}

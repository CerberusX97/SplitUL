package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.exceptions.ExpenseFutureDateException;
import ca.ulaval.glo2003.entities.exceptions.ExpenseInvalidDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpenseTest {

	private Expense expense;
	private final String VALID_DATE = "2015-11-11";
	private final String INVALID_DATE_FORMAT = "2015-1111";
	private final String INVALID_FUTURE_DATE = "2055-11-11";

	@BeforeEach
	public void setUp() {
		expense = new Expense();
	}

	@Test
	void whenConvertToDate_validDate_ThenConvertToDate() {

		assertThat(expense.convertToDate(VALID_DATE)).isInstanceOf(Date.class);
	}
	@Test
	void whenConvertToDate_InvalidDate_ThenThrowInvalidDateFormatException() {

		assertThrows(ExpenseInvalidDateFormat.class, () -> expense.convertToDate(INVALID_DATE_FORMAT));
	}
	@Test
	void whenConvertToDate_InvalidFutureDate_ThenThrowFutureDateException() {

		assertThrows(ExpenseFutureDateException.class, () -> expense.convertToDate(INVALID_FUTURE_DATE));
	}

}

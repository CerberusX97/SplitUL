package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.entities.exceptions.ExpenseFutureDateException;
import ca.ulaval.glo2003.entities.exceptions.ExpenseInvalidDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Expense {
	private UUID id;
	private String description;
	private double amount;
	private Date purchaseDate;
	private String paidBy;
	private UUID PAYER_ID;
	private Split split;

	public Expense() {
	}
	public Expense(String description, double amount, String date, Member payer, Split split) {
		this.id = UUID.randomUUID();
		this.description = description;
		this.amount = amount;
		this.purchaseDate = convertToDate(date);
		this.paidBy = payer.getMemberName();
		this.PAYER_ID = payer.getUuid();
		this.split = split;
	}
	public UUID getId() {
		return PAYER_ID;
	}
	public double getAmount() {
		return amount;
	}

	public Date convertToDate(String purchaseDateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date, currentDate;
		try {

			date = sdf.parse(purchaseDateString);
			currentDate = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			throw new ExpenseInvalidDateFormat();
		}
		if (date.after(currentDate)) {
			throw new ExpenseFutureDateException();
		}
		return date;

	}

}

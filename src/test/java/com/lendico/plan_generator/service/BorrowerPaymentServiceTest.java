package com.lendico.plan_generator.service;

import static com.lendico.plan_generator.utils.BorrowerPaymentFactory.createBorrowerPaymentDO;
import static com.lendico.plan_generator.utils.FormatUtils.convertStringToDateUTC;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.lendico.plan_generator.exception.LoanInvalidDataException;
import com.lendico.plan_generator.model.BorrowerPaymentDO;
import com.lendico.plan_generator.model.BorrowerPayments;
import com.lendico.plan_generator.model.LoanDO;
import com.lendico.plan_generator.service.impl.BorrowerPaymentServiceImpl;

public class BorrowerPaymentServiceTest {
	private BorrowerPaymentService borrowerPaymentService = new BorrowerPaymentServiceImpl();

	@Test
	public void generateValidTest() throws ParseException, LoanInvalidDataException {
		final int EXPECTED_LIST_SIZE = 3;

		LoanDO loan = new LoanDO(3, new BigDecimal("5.0"), new BigDecimal("5000"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		BorrowerPayments borrowerPayments = borrowerPaymentService.generate(loan);

		assertThat(borrowerPayments.getBorrowerPayments(), hasSize(EXPECTED_LIST_SIZE));
		assertThat(borrowerPayments.getBorrowerPayments(), is(expectedBorrowerPayments()));
	}

	@Test
	public void principalAmountExceedsInitialOutstandingAmountTest() throws ParseException, LoanInvalidDataException {
		final int EXPECTED_LIST_SIZE = 6;

		LoanDO loan = new LoanDO(6, new BigDecimal("5.0"), new BigDecimal("5000"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		BorrowerPayments borrowerPayments = borrowerPaymentService.generate(loan);

		assertThat(borrowerPayments.getBorrowerPayments(), hasSize(EXPECTED_LIST_SIZE));
		assertThat(borrowerPayments.getBorrowerPayments(), is(expectedPrincipalAmountExeceedsItialAmount()));
	}
	
	@Test
	public void invalidEntryZerosTest() throws ParseException {
		LoanDO loan = new LoanDO(null, new BigDecimal("0"), new BigDecimal("0"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		try {
			borrowerPaymentService.generate(loan);
			fail("No error was found during the test");
		} catch (Exception e) {
			assertThat(e, instanceOf(LoanInvalidDataException.class));
		}
	}

	@Test
	public void invalidEntryNullTest() throws ParseException {
		LoanDO loan = new LoanDO(24, null, new BigDecimal("5"), convertStringToDateUTC("2018-01-01T00:00:01Z"));
		try {
			borrowerPaymentService.generate(loan);
			fail("No error was found during the test");
		} catch (Exception e) {
			assertThat(e, instanceOf(LoanInvalidDataException.class));
		}
	}

	private List<BorrowerPaymentDO> expectedBorrowerPayments() throws ParseException {
		List<BorrowerPaymentDO> payments = new ArrayList<>();
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-01-01T00:00:01Z"), new BigDecimal("5000"), new BigDecimal("20.83"),
				new BigDecimal("1659.68"), new BigDecimal("3340.32")));
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-02-01T00:00:01Z"), new BigDecimal("3340.32"),
				new BigDecimal("13.92"), new BigDecimal("1666.59"), new BigDecimal("1673.73")));
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-03-01T00:00:01Z"), new BigDecimal("1673.73"), new BigDecimal("6.97"),
				new BigDecimal("1673.54"), new BigDecimal("0.19")));
		return payments;
	}

	private List<BorrowerPaymentDO> expectedPrincipalAmountExeceedsItialAmount() throws ParseException {
		List<BorrowerPaymentDO> payments = new ArrayList<>();
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.54"), 
				convertStringToDateUTC("2018-01-01T00:00:01Z"),
				new BigDecimal("5000"), 
				new BigDecimal("20.83"), 
				new BigDecimal("824.71"), 
				new BigDecimal("4175.29")));
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.54"), 
				convertStringToDateUTC("2018-02-01T00:00:01Z"),
				new BigDecimal("4175.29"), 
				new BigDecimal("17.40"), 
				new BigDecimal("828.14"),
				new BigDecimal("3347.15")));
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.54"), 
				convertStringToDateUTC("2018-03-01T00:00:01Z"),
				new BigDecimal("3347.15"), 
				new BigDecimal("13.95"), 
				new BigDecimal("831.59"),
				new BigDecimal("2515.56")));
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.54"), 
				convertStringToDateUTC("2018-03-31T23:00:01Z"),
				new BigDecimal("2515.56"), 
				new BigDecimal("10.48"), 
				new BigDecimal("835.06"),
				new BigDecimal("1680.50")));
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.54"), 
				convertStringToDateUTC("2018-04-30T23:00:01Z"),
				new BigDecimal("1680.50"), 
				new BigDecimal("7.00"), 
				new BigDecimal("838.54"), 
				new BigDecimal("841.96")));
		payments.add(createBorrowerPaymentDO(
				new BigDecimal("845.47"), 
				convertStringToDateUTC("2018-05-31T23:00:01Z"),
				new BigDecimal("841.96"), 
				new BigDecimal("3.51"), 
				new BigDecimal("841.96"), 
				new BigDecimal("0.00")));
		return payments;
	}
}

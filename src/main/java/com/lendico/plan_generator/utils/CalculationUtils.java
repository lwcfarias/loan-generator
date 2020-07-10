package com.lendico.plan_generator.utils;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.math.MathContext;
public class CalculationUtils {
	private static final BigDecimal MONTH_DAYS = new BigDecimal(30);
	private static final BigDecimal YEAR_DAYS = new BigDecimal(360);
	private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);
	private static final int MAX_DECIMAL_DIGITS = 6;
	private static final int MAX_DECIMAL_ROUND = 2;

	/**
	 * Calculates the interest
	 * 
	 * @param nominalRate                 Nominal rate
	 * @param initialOutstandingPrincipal Initial Outstanding Principal
	 * @return calculated interested
	 */
	public static BigDecimal calculateInterest(BigDecimal nominalRate, BigDecimal initialOutstandingPrincipal) {
		BigDecimal rate = nominalRate.divide(new BigDecimal(100));
		return rate.multiply(MONTH_DAYS.multiply(initialOutstandingPrincipal)).divide(YEAR_DAYS, MAX_DECIMAL_ROUND,
				HALF_UP);
	}

	/**
	 * Calculates the Principal
	 * 
	 * @param Interest calculated Interest
	 * @param annuity  Annuity
	 * @return Calculated principal
	 */
	public static BigDecimal calculatePrincipal(BigDecimal interest, BigDecimal annuity) {
		return annuity.subtract(interest);
	}

	public static BigDecimal calculateAnnuity(BigDecimal presentAmount, BigDecimal nominalRate,
			Integer numberOfPeriods) {

		BigDecimal reatePerPeriod = nominalRate
				.divide(MONTHS_IN_YEAR, MAX_DECIMAL_DIGITS, HALF_UP)
				.divide(new BigDecimal(100), MAX_DECIMAL_DIGITS, HALF_UP);
		numberOfPeriods = numberOfPeriods * -1;

		MathContext mc = new MathContext(6);
		return presentAmount.multiply(reatePerPeriod).divide(ONE.subtract(ONE.add(reatePerPeriod).pow(numberOfPeriods, mc)),
				MAX_DECIMAL_ROUND, HALF_UP);
	}
}

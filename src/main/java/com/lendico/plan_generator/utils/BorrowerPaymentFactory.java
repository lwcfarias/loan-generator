package com.lendico.plan_generator.utils;

import java.math.BigDecimal;
import java.util.Date;

import com.lendico.plan_generator.model.BorrowerPaymentDO;

public class BorrowerPaymentFactory {
	/*
	 * Creates the BorrowerPaymentDO object which represents string values for each
	 * value
	 */
	public static BorrowerPaymentDO createBorrowerPaymentDO(BigDecimal annuity, Date date,
			BigDecimal initialOutstandingPrincipal, BigDecimal interest, BigDecimal principal,
			BigDecimal remainingOutstandingPrincipal) {

		BorrowerPaymentDO BorrowerPaymentDO = new BorrowerPaymentDO(annuity, date,
				initialOutstandingPrincipal, interest, principal,
				remainingOutstandingPrincipal);
		return BorrowerPaymentDO;
	}
}

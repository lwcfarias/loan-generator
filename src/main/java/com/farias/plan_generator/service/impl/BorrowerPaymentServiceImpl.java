package com.farias.plan_generator.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.farias.plan_generator.exception.LoanInvalidDataException;
import com.farias.plan_generator.model.BorrowerPaymentDO;
import com.farias.plan_generator.model.BorrowerPayments;
import com.farias.plan_generator.model.LoanDO;
import com.farias.plan_generator.service.BorrowerPaymentService;
import com.farias.plan_generator.utils.BorrowerPaymentFactory;
import com.farias.plan_generator.utils.CalculationUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BorrowerPaymentServiceImpl implements BorrowerPaymentService {

	@Override
	public BorrowerPayments generate(LoanDO loan) throws LoanInvalidDataException {
		String method = "generate()";
		log.debug(method + "-> starting calculation");
		checkData(loan);

		List<BorrowerPaymentDO> borrowerPayments = new ArrayList<>();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(loan.getStartDate());

		BigDecimal initialOutstandingPrincipal = loan.getLoanAmount();
		BigDecimal annuity = CalculationUtils.calculateAnnuity(loan.getLoanAmount(), loan.getNominalRate(),
				loan.getDuration());

		for (int i = 0; i < loan.getDuration(); i++) {
			BigDecimal interest = CalculationUtils.calculateInterest(loan.getNominalRate(),
					initialOutstandingPrincipal);
			BigDecimal principal = CalculationUtils.calculatePrincipal(interest, annuity);
			//The initial outstanding principal amount, take initial outstanding principal amount instead.
			if (principal.compareTo(initialOutstandingPrincipal) == 1) {
				annuity = annuity.subtract(principal.subtract(initialOutstandingPrincipal));
				principal = initialOutstandingPrincipal;
			}
			BigDecimal remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);

			BorrowerPaymentDO BorrowerPaymentDO = BorrowerPaymentFactory.createBorrowerPaymentDO(annuity, startDate.getTime(),
					initialOutstandingPrincipal, interest, principal, remainingOutstandingPrincipal);

			startDate.add(Calendar.MONTH, 1);
			initialOutstandingPrincipal = remainingOutstandingPrincipal;

			borrowerPayments.add(BorrowerPaymentDO);
		}
		log.debug(method + " -> calculation finished. DATA[" + borrowerPayments + "]");
		return new BorrowerPayments(borrowerPayments);
	}

	//Checks if the content of the data is valid. It avoids wrong divisions.
	private void checkData(LoanDO loan) throws LoanInvalidDataException {
		String method = "checkData()";
		log.debug(method + "-> Checking." + loan.toString());
		if (loan.getDuration() == null || loan.getDuration() <= 0 || loan.getLoanAmount() == null
				|| loan.getLoanAmount().equals(BigDecimal.ZERO) || loan.getNominalRate() == null
				|| loan.getNominalRate().compareTo(BigDecimal.ZERO) <= 0 || loan.getStartDate() == null) {
			throw new LoanInvalidDataException();
		}
		log.debug(method + "-> Checked.");
	}
}

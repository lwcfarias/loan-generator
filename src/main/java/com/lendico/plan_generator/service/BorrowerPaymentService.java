package com.lendico.plan_generator.service;

import com.lendico.plan_generator.exception.LoanInvalidDataException;
import com.lendico.plan_generator.model.BorrowerPayments;
import com.lendico.plan_generator.model.LoanDO;

/**
 * Generates the borrower payments regards an specific loan.
 * @author Leandro Farias
 */
public interface BorrowerPaymentService {
	/**
	 * Generates all borrower payments 
	 * @param loan Information of the loan
	 * @return Borrower payments
	 */
	BorrowerPayments generate(LoanDO loan)  throws LoanInvalidDataException;
}

package com.farias.plan_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.farias.plan_generator.exception.LoanInvalidDataException;
import com.farias.plan_generator.model.BorrowerPayments;
import com.farias.plan_generator.model.LoanDO;
import com.farias.plan_generator.service.BorrowerPaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/generate-plan")
@Slf4j
public class PlanGeneratorController {
	
	private BorrowerPaymentService borrowerPaymentService;
	
	@Autowired
	public PlanGeneratorController(BorrowerPaymentService borrowerPaymentService) {
		this.borrowerPaymentService = borrowerPaymentService;
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public BorrowerPayments generate(@RequestBody LoanDO loan) {
		log.debug("generate-plan, requested");
		try {
			return getBorrowerPaymentService().generate(loan);
		} catch (LoanInvalidDataException e) {
			log.error("Invalid Data. Data must not be null or less than ONE", e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, inform a valid loan! Data must not be null or less than ONE.");
		}
	}
	
	protected BorrowerPaymentService getBorrowerPaymentService() {
		return this.borrowerPaymentService;
	}
}

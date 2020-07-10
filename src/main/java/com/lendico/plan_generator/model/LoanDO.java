package com.lendico.plan_generator.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class LoanDO {
	private Integer duration;
	private BigDecimal nominalRate;
	private BigDecimal loanAmount;
	private Date startDate;
}

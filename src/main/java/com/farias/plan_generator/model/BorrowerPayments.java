package com.farias.plan_generator.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BorrowerPayments {
	public  List<BorrowerPaymentDO> borrowerPayments;
}

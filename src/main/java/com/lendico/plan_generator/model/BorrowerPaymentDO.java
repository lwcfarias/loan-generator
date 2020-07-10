package com.lendico.plan_generator.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BorrowerPaymentDO {
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal borrowerPaymentAmount;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	private Date date;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal initialOutstandingPrincipal;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal interest;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal principal;
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal remainingOutstandingPrincipal;
}

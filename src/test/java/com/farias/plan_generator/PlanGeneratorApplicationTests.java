package com.farias.plan_generator;

import static com.farias.plan_generator.utils.BorrowerPaymentFactory.createBorrowerPaymentDO;
import static com.farias.plan_generator.utils.FormatUtils.convertStringToDateUTC;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.farias.plan_generator.model.BorrowerPaymentDO;
import com.farias.plan_generator.model.BorrowerPayments;
import com.farias.plan_generator.model.LoanDO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PlanGeneratorApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testBorrowerPayments() throws Exception {
		LoanDO loan = new LoanDO(3, new BigDecimal("5.0"), new BigDecimal("5000"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		callService(loan).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.borrowerPayments", hasSize(3)))
				.andExpect(MockMvcResultMatchers.content()
						.string(objectMapper.writeValueAsString(expectedBorrowerPayments())));
	}

	@Test
	public void testBorrowerPaymentsInvalidDataException() throws Exception {
		LoanDO loan = new LoanDO(0, new BigDecimal("5.0"), new BigDecimal("5000"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		callService(loan).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
				.status().reason("Please, inform a valid loan! Data must not be null or less than ONE."));
	}

	@Test
	public void testPrincipalAmountExceedsInitialAmount() throws Exception {
		LoanDO loan = new LoanDO(6, new BigDecimal("5.0"), new BigDecimal("5000"),
				convertStringToDateUTC("2018-01-01T00:00:01Z"));

		callService(loan).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.borrowerPayments", hasSize(6)))
				.andExpect(MockMvcResultMatchers.content()
						.string(objectMapper.writeValueAsString(expectedPrincipalAmountExeceedsItialAmount())));
	}

	private ResultActions callService(Object content) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.post("/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(content)));
	}

	private BorrowerPayments expectedBorrowerPayments() throws ParseException {
		List<BorrowerPaymentDO> payments = new ArrayList<>();
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-01-01T00:00:01Z"),
				new BigDecimal("5000"), new BigDecimal("20.83"), new BigDecimal("1659.68"), new BigDecimal("3340.32")));
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-02-01T00:00:01Z"),
				new BigDecimal("3340.32"), new BigDecimal("13.92"), new BigDecimal("1666.59"),
				new BigDecimal("1673.73")));
		payments.add(createBorrowerPaymentDO(new BigDecimal("1680.51"), convertStringToDateUTC("2018-03-01T00:00:01Z"),
				new BigDecimal("1673.73"), new BigDecimal("6.97"), new BigDecimal("1673.54"), new BigDecimal("0.19")));
		return new BorrowerPayments(payments);
	}

	private BorrowerPayments expectedPrincipalAmountExeceedsItialAmount() throws ParseException {
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
		return new BorrowerPayments(payments);
	}

}

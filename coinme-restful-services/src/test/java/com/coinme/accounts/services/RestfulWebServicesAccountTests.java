package com.coinme.accounts.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.coinme.accounts.services.controller.AccountController;
import com.coinme.accounts.services.dao.AccountDaoService;
import com.coinme.accounts.services.entity.Account;
import com.coinme.accounts.services.entity.Customer;
import com.coinme.accounts.services.entity.TransferHistory;
import com.coinme.accounts.services.request.CreateAccountRequest;
import com.coinme.accounts.services.request.TransferAmountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class)
public class RestfulWebServicesAccountTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountDaoService accountDao;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testCreateAccount() throws Exception {
		Account mockAccount = new Account(new Long(1001), "5351255893", new BigDecimal("8631.51"));
		Customer mockCustomer = new Customer(new Long(100), "Arisha Barron");
		CreateAccountRequest request = new CreateAccountRequest(mockCustomer, mockAccount);
		Mockito.when(accountDao.createAccount(Mockito.any(Customer.class), Mockito.any(Account.class)))
				.thenReturn(mockAccount);
		String json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/accounts/create").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void testTransferAmount() throws Exception {
		TransferAmountRequest request = new TransferAmountRequest("5351255893", "5351255895", new BigDecimal(100.24),
				"Test Transaction");
		Mockito.when(accountDao.transferAmount(Mockito.any(TransferAmountRequest.class))).thenReturn(Boolean.TRUE);
		String json = mapper.writeValueAsString(request);
		mockMvc.perform(put("/accounts/transfer").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testGetAccountBalance() throws Exception {
		Mockito.when(accountDao.retrieveBalance(Mockito.anyString())).thenReturn(BigDecimal.valueOf(8631.51));
		mockMvc.perform(get("/accounts/balance/{accountNumber}", "5351255893").contentType(MediaType.APPLICATION_JSON)
				.content("5351255893").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.equalTo(8631.51)));
	}

	@Test
	public void testGetTransferHistory() throws Exception {
		List<TransferHistory> mockTransferHistoryList = new ArrayList<>();
		mockTransferHistoryList.add(new TransferHistory(new Long(1), "5351255893", new BigDecimal(500.21), "DEBIT",
				"Test Debit", new Timestamp(System.currentTimeMillis())));
		mockTransferHistoryList.add(new TransferHistory(new Long(2), "5351255893", new BigDecimal(1000.83), "CREDIT",
				"Test Credit", new Timestamp(System.currentTimeMillis())));
		Mockito.when(accountDao.getTransferHistory(Mockito.anyString())).thenReturn(mockTransferHistoryList);
		mockMvc.perform(get("/accounts/history/{accountNumber}", "5351255893").contentType(MediaType.APPLICATION_JSON)
				.content("5351255893").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].accountNumber", Matchers.equalTo("5351255893")))
				.andExpect(jsonPath("$[0].transctionType", Matchers.equalTo("DEBIT")))
				.andExpect(jsonPath("$[1].amount", Matchers.equalTo(new BigDecimal(1000.83))));
	}
}

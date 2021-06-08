package com.coinme.accounts.services.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.coinme.accounts.services.dao.AccountDaoService;
import com.coinme.accounts.services.entity.Account;
import com.coinme.accounts.services.entity.TransferHistory;
import com.coinme.accounts.services.request.CreateAccountRequest;
import com.coinme.accounts.services.request.TransferAmountRequest;

/**
 * 
 * @author sande
 *
 */
@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

	@Autowired
	private AccountDaoService accountDao;

	/**
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<Object> createAccount(@Valid @RequestBody CreateAccountRequest request) {
		Account account = accountDao.createAccount(request.getCustomer(), request.getAccount());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@PutMapping(value = "/transfer")
	public ResponseEntity<Object> transferAmount(@RequestBody TransferAmountRequest request) {
		Boolean isTransferSuccess = accountDao.transferAmount(request);
		if (isTransferSuccess)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	@GetMapping(value = "/balance/{accountNumber}")
	public BigDecimal getAccountBalance(@PathVariable String accountNumber) {
		return accountDao.retrieveBalance(accountNumber);
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	@GetMapping(value = "/history/{accountNumber}")
	public List<TransferHistory> getTransferHistory(@PathVariable String accountNumber) {
		return accountDao.getTransferHistory(accountNumber);
	}
}

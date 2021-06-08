package com.coinme.accounts.services.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coinme.accounts.services.entity.Account;
import com.coinme.accounts.services.entity.Customer;
import com.coinme.accounts.services.entity.TransferHistory;
import com.coinme.accounts.services.enums.TransactionType;
import com.coinme.accounts.services.exception.AccountNotFoundException;
import com.coinme.accounts.services.exception.InsufficientBalanceException;
import com.coinme.accounts.services.repository.AccountRepository;
import com.coinme.accounts.services.repository.CustomerRepository;
import com.coinme.accounts.services.repository.TransactionRepository;
import com.coinme.accounts.services.request.TransferAmountRequest;

@Component
public class AccountDaoService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * 
	 * @param customer
	 * @param account
	 * @return
	 */
	public Account createAccount(Customer customer, Account account) {
		Customer savedCustomer = customerRepository.save(customer);
		account.setCustomer(savedCustomer);
		return accountRepository.save(account);
	}

	/**
	 * 
	 * @param transferRequest
	 * @return
	 */
	public Boolean transferAmount(TransferAmountRequest transferRequest) {
		Account fromAccount = accountRepository.getOne(transferRequest.getFromAccount());
		Account toAccount = accountRepository.getOne(transferRequest.getToAccount());
		BigDecimal transferAmount = transferRequest.getAmount();
		String remarks = transferRequest.getRemarks();
		if (fromAccount == null || toAccount == null) {
			throw new AccountNotFoundException(fromAccount == null ? transferRequest.getFromAccount()
					: transferRequest.getToAccount() + "  doesn't exist");
		} else if (fromAccount.getBalance().compareTo(transferAmount) == -1) {
			throw new InsufficientBalanceException(
					"Account number " + fromAccount.getAccountNumber() + " doesn't have sufficient balance.");
		} else {
			fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
			accountRepository.save(fromAccount);
			toAccount.setBalance(toAccount.getBalance().add(transferAmount));
			accountRepository.save(toAccount);
			transactionRepository.save(new TransferHistory(0L, fromAccount.getAccountNumber(), transferAmount,
					TransactionType.DEBIT.toString(), remarks, new Timestamp(System.currentTimeMillis())));
			transactionRepository.save(new TransferHistory(0L, toAccount.getAccountNumber(), transferAmount,
					TransactionType.CREDIT.toString(), remarks, new Timestamp(System.currentTimeMillis())));
			return true;
		}
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public BigDecimal retrieveBalance(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException(accountNumber + "  doesn't exist");
		} else {
			return account.getBalance();
		}

	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public List<TransferHistory> getTransferHistory(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException(accountNumber + "  doesn't exist");
		} else {
			 return transactionRepository.findByAccountNumber(accountNumber);
		}

	}
}

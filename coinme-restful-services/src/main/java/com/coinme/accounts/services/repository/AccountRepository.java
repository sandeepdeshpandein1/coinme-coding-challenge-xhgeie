package com.coinme.accounts.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinme.accounts.services.entity.Account;


public interface AccountRepository extends JpaRepository<Account, String>{

	Account findByAccountNumber (String accountNumber);
}

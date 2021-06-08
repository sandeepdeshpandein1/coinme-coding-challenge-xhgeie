package com.coinme.accounts.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinme.accounts.services.entity.TransferHistory;

public interface TransactionRepository extends JpaRepository<TransferHistory, Long> {

	List<TransferHistory> findByAccountNumber (String accountNumber);
	
}

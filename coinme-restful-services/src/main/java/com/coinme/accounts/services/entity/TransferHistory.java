package com.coinme.accounts.services.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferHistory {

	@Id
	@GeneratedValue
	private Long id;

	private String accountNumber;

	private BigDecimal amount;

	private String transctionType;

	private String remarks;

	private Timestamp transactionTime;

	@Override
	public String toString() {
		return "TransferHistory [id=" + id + ", accountNumber=" + accountNumber + ", amount=" + amount
				+ ", transctionType=" + transctionType + ", remarks=" + remarks + ", transactionTime=" + transactionTime
				+ "]";
	}

}

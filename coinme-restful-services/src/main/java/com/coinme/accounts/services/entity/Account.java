package com.coinme.accounts.services.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String accountNumber;
	@NotNull
	private BigDecimal balance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Customer customer;

	public Account(Long id, @NotNull String accountNumber, @NotNull BigDecimal balance) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", balance=" + balance + ", customer="
				+ customer + "]";
	}

}

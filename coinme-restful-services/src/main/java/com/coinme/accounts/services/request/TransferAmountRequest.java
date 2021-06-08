package com.coinme.accounts.services.request;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.coinme.accounts.services.entity.Account;
import com.coinme.accounts.services.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountRequest {

	private String fromAccount;

	private String toAccount;

	private BigDecimal amount;

	private String remarks;
	
	

	@Override
	public String toString() {
		return "TransferAmountRequest [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount
				+ ", remarks=" + remarks + "]";
	}

}

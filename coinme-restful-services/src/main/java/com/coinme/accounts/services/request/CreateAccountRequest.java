package com.coinme.accounts.services.request;

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
public class CreateAccountRequest {

	private Customer customer;

	private Account account;

}

package com.coinme.accounts.services.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	@Size(min = 2, message = "Name should have atleast 2 characters")
	private String name;

	@OneToMany(mappedBy = "customer")
	private List<Account> accounts;

	public Customer(Long id, @Size(min = 2, message = "Name should have atleast 2 characters") String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}

}

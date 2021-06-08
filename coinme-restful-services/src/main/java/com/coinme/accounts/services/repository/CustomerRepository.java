package com.coinme.accounts.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinme.accounts.services.entity.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

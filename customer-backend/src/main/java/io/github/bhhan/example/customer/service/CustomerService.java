package io.github.bhhan.example.customer.service;

import io.github.bhhan.example.common.domain.Money;
import io.github.bhhan.example.customer.domain.Customer;
import io.github.bhhan.example.customer.domain.CustomerCreditLimitExceededException;
import io.github.bhhan.example.customer.domain.CustomerNotFoundException;
import io.github.bhhan.example.customer.domain.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer createCustomer(String name, Money creditLimit) {
    Customer customer  = new Customer(name, creditLimit);
    return customerRepository.save(customer);
  }

  public void reserveCredit(long customerId, long orderId, Money orderTotal) throws CustomerCreditLimitExceededException {
    Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    customer.reserveCredit(orderId, orderTotal);
  }
}

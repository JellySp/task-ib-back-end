package com.jelly.taskibbackend.controller;


import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.repository.CustomerRepository;
import com.jelly.taskibbackend.utils.LoanCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // This isnt called anywhere in the front end app
    // It's only here for my own testing purposes and
    // I feel this would be implemented later in a real
    // world case
    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder) {

        customerRepository.save(customer);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/customers/{pic}").build(customer.getPic()))
                .build();
    }

    @GetMapping("/findAllCustomers")
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/findByPic")
    public Customer findByPic(@RequestParam String pic) {
        return customerRepository.findByPic(pic);
    }

    // verifies customer data after submitting their application to check for data tampering
    @GetMapping("/checkIsEligibleForAnyLoan")
    public Boolean isEligibleForAnyLoan(@RequestParam String pic) {
        Customer customer = customerRepository.findByPic(pic);
        LoanCalculator loanCalculator = new LoanCalculator();
        return loanCalculator.isEligibleForAnyLoan(customer.getCreditModifier());
    }

    @GetMapping("/checkCustomerExistsOnDataBase")
    public Boolean customerExistsOnDataBase(@RequestParam String pic) {
        Customer customer = customerRepository.findByPic(pic);

        return customer != null;
    }
}

package com.jelly.taskibbackend.controller;


import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.repository.CustomerRepository;
import com.jelly.taskibbackend.utils.LoanParametersChecker;
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
    public ResponseEntity addCustomer(@RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder) {
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

    @GetMapping("/verifyCustomerData")
    public Boolean verifyCustomerData(@RequestParam String pic,@RequestParam double loanAmount, @RequestParam double loanPeriod) {

        // this functions rechecks the credit
        // score after applying for loan to
        // prevent the customer from injecting
        // bad values into their browser
        System.out.println("testverify");
        Customer customer = customerRepository.findByPic(pic);
        System.out.println(LoanParametersChecker.isCorrectParameters(customer,loanAmount,loanPeriod));
        return LoanParametersChecker.isCorrectParameters(customer, loanAmount, loanPeriod);
    }
}

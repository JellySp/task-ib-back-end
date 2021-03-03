package com.jelly.taskibbackend.controller;


import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/addCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return ResponseEntity.ok(HttpStatus.OK);
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

        // this functions rechecks the credit score after applying for loan to prevent the customer from injecting a
        // bad values into their browser
        Customer customer = customerRepository.findByPic(pic);
        return isCorrectCreditScore(customer.getCreditModifier(), loanAmount, loanPeriod) && isCorrectLoanAmount(loanAmount) && isCorrectLoanPeriod(loanPeriod);
    }


    //not entirely sure if I should be having  non mapped methods in this class but without using these,
    // the isCorrectData() method would look super ugly

    private boolean isCorrectCreditScore(int creditModifier, double loanAmount, double loanPeriod) {
        return ((creditModifier / loanAmount) * loanPeriod) >= 1;
    }

    private boolean isCorrectLoanAmount(double loanAmount) {
        return loanAmount >= 2000 && loanAmount <= 10_000;
    }

    private boolean isCorrectLoanPeriod(double loanPeriod) {
        return loanPeriod >= 12 && loanPeriod <= 60;
    }




}

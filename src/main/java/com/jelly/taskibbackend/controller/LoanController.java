package com.jelly.taskibbackend.controller;

import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.model.LoanOffer;
import com.jelly.taskibbackend.utils.LoanCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping
public class LoanController {

    @Autowired
    CustomerController customerController;



    @GetMapping("/getLoanOffer")
    public LoanOffer findByPic(@RequestParam String pic, @RequestParam double loanAmount, double loanPeriod) {
        LoanCalculator loanCalculator = new LoanCalculator();

        return loanCalculator.getLoanOffer(customerController.findByPic(pic), loanAmount, loanPeriod);
    }
}

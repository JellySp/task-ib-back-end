package com.jelly.taskibbackend.controller;

import com.jelly.taskibbackend.model.LoanOffer;
import com.jelly.taskibbackend.utils.LoanCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@CrossOrigin
@RestController
@RequestMapping
@Validated
public class LoanController {

    @Autowired
    CustomerController customerController;



    @GetMapping("/getLoanOffer")
    public LoanOffer getLoanOffer(@RequestParam String pic, @RequestParam @Min(2000) @Max(10000) double loanAmount, @RequestParam @Min(12) @Max(60) double loanPeriod) {
        LoanCalculator loanCalculator = new LoanCalculator();

        return loanCalculator.getLoanOffer(customerController.findByPic(pic), loanAmount, loanPeriod);
    }
}

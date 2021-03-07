package com.jelly.taskibbackend.controller;

import com.jelly.taskibbackend.exception.InvalidLoanParametersException;
import com.jelly.taskibbackend.model.LoanOffer;
import com.jelly.taskibbackend.utils.LoanCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;
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

    public LoanOffer getLoanOffer(@RequestParam String pic, @RequestParam double loanAmount, @RequestParam double loanPeriod) {
        if (loanAmount < 2000 || loanAmount > 10_000 || loanPeriod < 12 || loanPeriod > 60) {
            throw new InvalidLoanParametersException();
        }

        LoanCalculator loanCalculator = new LoanCalculator();
        return loanCalculator.getLoanOffer(customerController.findByPic(pic), loanAmount, loanPeriod);
    }
}

@ControllerAdvice
class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { InvalidLoanParametersException.class })
    public ResponseEntity<Object> handleException(InvalidLoanParametersException e) {
        logger.error("InvalidLoanParametersException: " + e.getMessage());
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

package com.jelly.taskibbackend.utils;

import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.model.LoanOffer;


public class LoanCalculator {
    public  boolean isEligibleForAnyLoan(double creditModifier) {
        return (creditModifier / 2000) * 60 >= 1;
    }

    public static int calculateMaxAmountForChosenPeriod(Customer customer, double loanPeriod) {
        if( (int) Math.ceil((customer.getCreditModifier()) * loanPeriod) >= 10_000) {
            return 10_000;
        }
        return (int) Math.ceil((customer.getCreditModifier()) * loanPeriod);
    }

    public static int calculateMinPeriodForChosenAmount(Customer customer, double loanAmount) {
        if ((int) Math.ceil(loanAmount / customer.getCreditModifier()) <= 12) {
            return 12;
        }
        return (int) Math.ceil(loanAmount / customer.getCreditModifier());

    }

    public LoanOffer getLoanOffer(Customer customer, double loanAmount, double loanPeriod) {
        LoanOffer loanOffer = new LoanOffer();
        loanOffer.setMinPeriodForCurrentAmount(calculateMinPeriodForChosenAmount(customer, loanAmount));
        loanOffer.setMaxAmountForCurrentPeriod(calculateMaxAmountForChosenPeriod(customer, loanPeriod));
        return  loanOffer;
    }


}


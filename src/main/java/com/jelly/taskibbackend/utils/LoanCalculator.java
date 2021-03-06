package com.jelly.taskibbackend.utils;

import com.jelly.taskibbackend.model.Customer;



public class LoanCalculator {
    public static boolean isEligibleForAnyLoan(double creditModifier) {
        return (creditModifier / 2000) * 60 >= 1;
    }

    static int calculateMaxAmountForChosenPeriod(Customer customer) {
        return 0;
    }




}


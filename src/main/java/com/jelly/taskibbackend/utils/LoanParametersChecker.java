package com.jelly.taskibbackend.utils;

import com.jelly.taskibbackend.model.Customer;

// To be honest I am not sure if I should put this class here,
// name it like this or have it at all but without it,
// the CustomerController class was getting too messy
// TODO find a better solution for this
public class LoanParametersChecker {

    public static boolean isCorrectParameters(Customer customer, double loanAmount, double loanPeriod) {

        // below return statement looks quite ugly but
        // I don't want to put all the below methods into
        // one for readability concerns
        // TODO find a better way
        return isCorrectCreditScore(customer, loanAmount, loanPeriod) && isCorrectLoanAmount(loanAmount) && isCorrectLoanPeriod(loanPeriod);
    }

    private static boolean isCorrectCreditScore(Customer customer, double loanAmount, double loanPeriod) {
        return ((customer.getCreditModifier() / loanAmount) * loanPeriod) >= 1;
    }

    private static boolean isCorrectLoanAmount(double loanAmount) {
        return loanAmount >= 2000 && loanAmount <= 10_000;
    }

    private static boolean isCorrectLoanPeriod(double loanPeriod) {
        return loanPeriod >= 12 && loanPeriod <= 60;
    }
}

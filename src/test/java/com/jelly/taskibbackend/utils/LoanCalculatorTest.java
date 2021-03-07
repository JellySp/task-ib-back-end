package com.jelly.taskibbackend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoanCalculatorTest {

    @Test
    void testIsEligibleForAnyLoan() {
        LoanCalculator loanCalculator = new LoanCalculator();
        assertTrue(loanCalculator.isEligibleForAnyLoan(34));
        assertFalse(loanCalculator.isEligibleForAnyLoan(33));
    }

}
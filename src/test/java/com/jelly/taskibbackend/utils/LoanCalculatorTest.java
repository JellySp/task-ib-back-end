package com.jelly.taskibbackend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {

    @Test
    void testIsEligibleForAnyLoan() {
        assertTrue(LoanCalculator.isEligibleForAnyLoan(34));
        assertFalse(LoanCalculator.isEligibleForAnyLoan(33));
    }

}
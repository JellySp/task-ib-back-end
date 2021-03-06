package com.jelly.taskibbackend.model;

import lombok.Data;

@Data
public class LoanOffer {
    int maxAmountForCurrentPeriod;
    int minPeriodForCurrentAmount;
}

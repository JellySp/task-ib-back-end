package com.jelly.taskibbackend.controller;

import com.jelly.taskibbackend.exception.InvalidLoanParametersException;
import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.repository.CustomerRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @MockBean
    private CustomerController customerController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void testGetLoanOfferWithValidParameters() throws Exception {
        when(customerController.findByPic("00000000034"))
                .thenReturn(new Customer(1L, "Test", "ThirtyFour", "00000000034", 34));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000000034&loanAmount=2000&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxAmountForCurrentPeriod").value(408))
                .andExpect(MockMvcResultMatchers.jsonPath("$.minPeriodForCurrentAmount").value(59));

        when(customerController.findByPic("00000001000"))
                .thenReturn(new Customer(1L, "Test", "OneThousand", "00000001000", 1000));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000001000&loanAmount=2000&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxAmountForCurrentPeriod").value(10_000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.minPeriodForCurrentAmount").value(12));
    }

    @Test
    void testGetLoanOfferWithInvalidParameters() throws Exception {
        when(customerController.findByPic("00000000034"))
                .thenReturn(new Customer(1L, "Test", "ThirtyFour", "00000000034", 34));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000000034&loanAmount=1999&loanPeriod=12"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidLoanParametersException));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000000034&loanAmount=10001&loanPeriod=12"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidLoanParametersException));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000000034&loanAmount=2000&loanPeriod=11"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidLoanParametersException));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getLoanOffer?pic=00000000034&loanAmount=2000&loanPeriod=61"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidLoanParametersException));


    }
}


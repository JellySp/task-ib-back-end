package com.jelly.taskibbackend.controller;

import com.jelly.taskibbackend.model.Customer;
import com.jelly.taskibbackend.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void testAddCustomer() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/addCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Test\", \"lastName\": \"AddUser\", \"pic\":\"39000000000\", \"creditModifier\": \"300\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void testFindAllCustomers() throws Exception {
        when(customerRepository.findAll())
                .thenReturn(List.of(new Customer(1L, "Test", "AllCustomers", "39000000000", 1000)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/findAllCustomers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pic").value("39000000000"));
    }

    @Test
    void testFindCustomerByPic() throws Exception {
        when(customerRepository.findByPic("39000000000"))
                .thenReturn(new Customer(1L, "Test", "Pic", "39000000000", 0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/findByPic?pic=39000000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pic").value("39000000000"));
    }


    @Test
    void testIsEligibleForAnyLoan() throws Exception {
        when(customerRepository.findByPic("00000000034"))
                .thenReturn(new Customer(1L, "Test", "ThirtyFour", "00000000034", 34));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/checkIsEligibleForAnyLoan?pic=00000000034"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        when(customerRepository.findByPic("00000000033"))
                .thenReturn(new Customer(1L, "Test", "ThirtyThree", "00000000033", 33));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/checkIsEligibleForAnyLoan?pic=00000000033"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "ThirtyThree", "00000000000", 0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/checkIsEligibleForAnyLoan?pic=00000000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));


    }

    @Test
    void testCustomerExistsOnDataBase() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "ThirtyThree", "00000000000", 0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/checkCustomerExistsOnDataBase?pic=00000000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/checkCustomerExistsOnDataBase?pic=00000000001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

    }


}
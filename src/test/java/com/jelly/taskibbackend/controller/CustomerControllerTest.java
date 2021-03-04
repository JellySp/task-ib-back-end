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


import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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
                .thenReturn(new Customer(1L,"Test","Pic","39000000000",0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/findByPic?pic=39000000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pic").value("39000000000"));
    }

    @Test
    void testVerifyCustomerDataWithModifierZero() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L,"Test","Zero","00000000000",0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

    }

    @Test
    void testVerifyCustomerDataWithModifierThirtyThree() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L,"Test","Zero","00000000000",33));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

    }

    @Test
    void testVerifyCustomerDataWithModifierThirtyFour() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L,"Test","Zero","00000000000",34));

        // tests for both maximum and minimum sums and periods

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2040&loanPeriod=59"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2040&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2041&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=59"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=58"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));
    }

    @Test
    void testVerifyCustomerDataWithModifierOneHundred() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "Zero", "00000000000", 100));

        // tests for both maximum and minimum sums and periods

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=6000&loanPeriod=59"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=6000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=6001&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=20"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=19"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));
    }

    @Test
    void testVerifyCustomerDataWithModifierThreeHundred() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "Zero", "00000000000", 300));

        // tests for both maximum and minimum sums and periods



        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=3600&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=3601&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));
    }

    @Test
    void testVerifyCustomerDataWithModifierOneThousand() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "Zero", "00000000000", 1000));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }

    @Test
    void testVerifyCustomerDataWithInvalidAmounts() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "Zero", "00000000000", 1000));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10001&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=1999&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

    }

    @Test
    void testVerifyCustomerDataWithInvalidPeriods() throws Exception {
        when(customerRepository.findByPic("00000000000"))
                .thenReturn(new Customer(1L, "Test", "Zero", "00000000000", 1000));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=10000&loanPeriod=61"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/verifyCustomerData?pic=00000000000&loanAmount=2000&loanPeriod=11"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));

    }


}
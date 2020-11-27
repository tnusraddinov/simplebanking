package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = AccountController.class)
//@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Account> accountList;

    @BeforeEach
    void setUp() {
        this.accountList = new ArrayList<>();
        this.accountList.add(new Account("Kerem Karaca", "17892"));
    }


    @Test
    public void givenId_Credit_thenReturnJson() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        TransactionStatus status = new TransactionStatus("OK", "");
        DepositTransaction transaction = new DepositTransaction(1000.0);

        given(accountService.findAccount("17892")).willReturn(account);
        given(accountService.credit("17892", new DepositTransaction(1000.0))).willReturn(status);
        given(accountService.credit2("17892", transaction)).willReturn(account);

        ResponseEntity<Account> result2 = controller.credit2( "17892", transaction);

        MvcResult mvcResult1 = this.mockMvc.perform(
                    post("/account/v1/credit2/17892").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction))
                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accountNumber", is(account.getAccountNumber())))
//                .andExpect(jsonPath("$.owner", is(account.getOwner())))
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(
                post("/account/v1/credit/17892").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction))
        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accountNumber", is(account.getAccountNumber())))
//                .andExpect(jsonPath("$.owner", is(account.getOwner())))
                .andReturn();

        MvcResult mvcResult = this.mockMvc.perform(get("/account/v1/{id}", "17892"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(account.getAccountNumber())))
                .andExpect(jsonPath("$.owner", is(account.getOwner()))).andReturn();

        System.out.println("Status  " + mvcResult1.getResponse().getContentAsString());
        System.out.println("Account " + mvcResult.getResponse().getContentAsString());
        System.out.println("Account2 " + objectMapper.writeValueAsString(account));

        String s= "adf";

    }
}

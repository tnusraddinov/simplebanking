package com.eteration.simplebanking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.DepositTransactionRepository;
import com.eteration.simplebanking.repository.PhoneBillPaymentTransactionRepository;
import com.eteration.simplebanking.repository.WithdrawalTransactionRepository;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Wrapper;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DepositTransactionRepository depositTransactionRepository;

    @Autowired
    WithdrawalTransactionRepository withdrawalTransactionRepository;

    @Autowired
    PhoneBillPaymentTransactionRepository phoneBillPaymentTransactionRepository;

    @BeforeEach
    public void setUp(){
        if(depositTransactionRepository.findAll().size()>0){
            depositTransactionRepository.deleteAll();
        }
        if(withdrawalTransactionRepository.findAll().size()>0){
            withdrawalTransactionRepository.deleteAll();
        }
        if(phoneBillPaymentTransactionRepository.findAll().size()>0){
            phoneBillPaymentTransactionRepository.deleteAll();
        }
        if(accountRepository.findAll().size()>0){
            accountRepository.deleteAll();
        }
        Account account = new Account("Kerem Karaca", "17892");
        accountRepository.save(account);
    }

    @Test
    public void givenId_Credit_thenReturnJson() throws Exception {
        ResponseEntity<TransactionStatus> responseEntity1 = restTemplate.postForEntity("/account/v1/credit/17892",
                new DepositTransaction(1000.0), TransactionStatus.class);

        ResponseEntity<Account> accountResponseEntity = restTemplate.getForEntity("/account/v1/17892", Account.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("OK", responseEntity1.getBody().getStatus());
        assertEquals(1000.0, accountResponseEntity.getBody().getBalance(), 0.001);

    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() throws Exception {
        
        ResponseEntity<TransactionStatus> responseEntity1 = restTemplate.postForEntity("/account/v1/credit/17892",
                new DepositTransaction(1000.0), TransactionStatus.class);


        ResponseEntity<TransactionStatus> responseEntity2 = restTemplate.postForEntity("/account/v1/debit/17892",
                new WithdrawalTransaction(50.0), TransactionStatus.class);

        ResponseEntity<Account> accountResponseEntity = restTemplate.getForEntity("/account/v1/17892", Account.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("OK", responseEntity1.getBody().getStatus());
        assertEquals("OK", responseEntity2.getBody().getStatus());
        assertEquals(950.0, accountResponseEntity.getBody().getBalance(), 0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
        throws Exception {

        ResponseEntity<TransactionStatus> responseEntity1 = restTemplate.postForEntity("/account/v1/credit/17892",
                new DepositTransaction(1000.0), TransactionStatus.class);


        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/account/v1/debit/17892",
                new WithdrawalTransaction(5000.0), String.class);

        ResponseEntity<Account> accountResponseEntity = restTemplate.getForEntity("/account/v1/17892", Account.class);

        // then
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("OK", responseEntity1.getBody().getStatus());
        assertEquals(1000.0, accountResponseEntity.getBody().getBalance(), 0.001);

        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals("InsufficientBalance", responseEntity2.getBody());

    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}

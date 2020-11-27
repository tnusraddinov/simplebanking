package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.graalvm.compiler.lir.amd64.AMD64CCall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class Controller2Tests {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<DepositTransaction> depositTransactionJacksonTester;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        ResponseEntity<TransactionStatus> transactionStatusResponseEntity = restTemplate.postForEntity("/account/v1/credit/17892",
                new DepositTransaction(1000.0), TransactionStatus.class);

        ResponseEntity<Account> accountResponseEntity = restTemplate.getForEntity("/account/v1/17892", Account.class);

        // then
        assertThat(transactionStatusResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("OK", transactionStatusResponseEntity.getBody().getStatus());
        assertEquals(1000.0, accountResponseEntity.getBody().getBalance());


        Account account = new Account("Kerem Karaca", "17892");
//        DepositTransaction depositTransaction = new DepositTransaction(1000.0);
//        TransactionStatus transactionStatus = new TransactionStatus("OK", "");
//        ResponseEntity<Account> accountResponseEntity = ResponseEntity.ok(account);
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
////        when(service.credit( "17892", depositTransaction)).thenReturn(transactionStatus);
//        doReturn(transactionStatus).when(service).credit( "17892", depositTransaction);
//        doReturn(accountResponseEntity).when(controller).getAccount( "17892");
//
//        ResponseEntity<TransactionStatus> responseEntity2 = controller.credit("17892", depositTransaction);
//        ResponseEntity<Account> responseEntity = controller.getAccount("17892");
//
//        System.out.println("RespEntityTransStatus " + responseEntity2.getStatusCodeValue());
//        System.out.println("TransStatus " + responseEntity2.getBody().getApprovalCode());
//
//        System.out.println("RespEntityCode " + responseEntity.getStatusCodeValue());
//        System.out.println("RespEntity " + responseEntity.getBody().getBalance());
//        System.out.println("AccRespEntity " + accountResponseEntity.getBody().getBalance());
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

//        doReturn(account).when(service).findAccount( "17892");
//        doReturn(transactionStatus).when(service).credit("17892", depositTransaction);
//
//        ResponseEntity<Account> result = controller.getAccount( "17892");
//        ResponseEntity<TransactionStatus> result2 = controller.credit( "17892", depositTransaction);
//
//        verify(service, times(1)).findAccount("17892");
//        verify(service, times(1)).credit("17892", depositTransaction);
//
//        assertEquals("OK", result2.getBody().getStatus());

    }


}

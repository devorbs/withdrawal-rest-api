// package com.enviro.assessment.grad001.bokangmakibinye.controller;

// import com.enviro.assessment.grad001.bokangmakibinye.exceptions.InvalidWithdrawalRequestException;
// import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeRequest;
// import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalResponse;
// import com.enviro.assessment.grad001.bokangmakibinye.service.WithdrawalService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.math.BigDecimal;
// import java.math.RoundingMode;

// @WebMvcTest(controllers = WithdrawalNoticeController.class)
// @AutoConfigureMockMvc(addFilters = false)
// @ExtendWith(MockitoExtension.class)
// class WithdrawalNoticeControllerTests {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @MockBean
//     private WithdrawalService withdrawalService;


//     @Test
//     void createWithdrawal_ValidRequest_ReturnsOk() throws Exception {
//         WithdrawalNoticeRequest validRequest = createValidWithdrawalRequest();
//         WithdrawalResponse mockResponse = createMockWithdrawalResponse();

//         given(withdrawalService.createWithdrawal(any(WithdrawalNoticeRequest.class))).willReturn(mockResponse);

//         mockMvc.perform(post("/withdrawals/create")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(validRequest)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.newBalance").value(mockResponse.getNewBalance().setScale(1, RoundingMode.HALF_UP)))
//                 .andExpect(jsonPath("$.previousBalance").value(mockResponse.getPreviousBalance().setScale(1, RoundingMode.HALF_UP)))
//                 .andExpect(jsonPath("$.withdrawnBalance").value(mockResponse.getWithdrawnBalance().setScale(1, RoundingMode.HALF_UP)));
//     }

//     @Test
//     void createWithdrawal_InvalidRequest_ReturnsBadRequest() throws Exception {
//         WithdrawalNoticeRequest invalidRequest = createInvalidWithdrawalRequest();
//         String errorMessage = "Invalid request error message";

//         given(withdrawalService.createWithdrawal(any(WithdrawalNoticeRequest.class)))
//                 .willThrow(new InvalidWithdrawalRequestException(errorMessage));

//         mockMvc.perform(post("/withdrawals/create")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(invalidRequest)))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(content().string("Failed to create withdrawal notice: " + errorMessage));
//     }

//     // Helper methods to create test data
//     private WithdrawalNoticeRequest createValidWithdrawalRequest() {
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setBankName("TestBank");
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);
//         withdrawalRequest.setWithdrawalAmount(new BigDecimal("1000.00"));
//         withdrawalRequest.setAccountHolderDetails("John Doe");

//         return withdrawalRequest;
//     }

//     private WithdrawalNoticeRequest createInvalidWithdrawalRequest() {
//         return new WithdrawalNoticeRequest();
//     }

//     private WithdrawalResponse createMockWithdrawalResponse() {
//         WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
//         withdrawalResponse.setNewBalance(new BigDecimal("9000.00"));
//         withdrawalResponse.setPreviousBalance(new BigDecimal("10000.00"));
//         withdrawalResponse.setWithdrawnBalance(new BigDecimal("1000.00"));

//         return withdrawalResponse;
//     }
// }


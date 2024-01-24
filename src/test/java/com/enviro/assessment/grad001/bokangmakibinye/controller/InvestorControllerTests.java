// package com.enviro.assessment.grad001.bokangmakibinye.controller;


// import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
// import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
// import com.enviro.assessment.grad001.bokangmakibinye.service.InvestorService;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class InvestorControllerTests {

//     @Mock
//     private InvestorService investorService;


//     @Test
//     void getInvestorById_ExistingInvestor_ReturnsInvestor() {
//         // Arrange
//         Long investorId = 1L;
//         Investor mockInvestor = new Investor();
//         when(investorService.getInvestorById(investorId)).thenReturn(Optional.of(mockInvestor));

//         // Act
//         InvestorController investorController = new InvestorController(investorService);
//         Investor result = investorController.getInvestorById(investorId).getBody();

//         // Assert

//     }

//     @Test
//     void getInvestorById_NonExistingInvestor_ThrowsInvestorNotFoundException() {
//         // Arrange
//         Long investorId = 1L;
//         when(investorService.getInvestorById(investorId)).thenReturn(Optional.empty());

//         // Act
//         InvestorController investorController = new InvestorController(investorService);

//         // Assert

//     }
// }


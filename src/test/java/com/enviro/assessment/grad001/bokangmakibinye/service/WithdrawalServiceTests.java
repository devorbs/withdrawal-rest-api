// package com.enviro.assessment.grad001.bokangmakibinye.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.enviro.assessment.grad001.bokangmakibinye.exceptions.InvalidWithdrawalAmountException;
// import com.enviro.assessment.grad001.bokangmakibinye.exceptions.InvalidWithdrawalRequestException;
// import com.enviro.assessment.grad001.bokangmakibinye.exceptions.ProductNotAvailableException;
// import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
// import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
// import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeRequest;
// import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalResponse;
// import com.enviro.assessment.grad001.bokangmakibinye.repository.InvestorRepository;
// import com.enviro.assessment.grad001.bokangmakibinye.repository.ProductRepository;
// import com.enviro.assessment.grad001.bokangmakibinye.repository.WithdrawalRepository;

// @ExtendWith(MockitoExtension.class)
// public class WithdrawalServiceTests {
    
//     @Mock
//     private WithdrawalRepository withdrawalRepository;

//     @Mock
//     private ProductRepository productRepository;

//     @Mock
//     private InvestorRepository investorRepository;

//     @InjectMocks
//     private WithdrawalService withdrawalService;


//     @Test
//     public void testCreateWithdrawal() {
//         // Mock data
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setAccountHolderDetails("John Doe");
//         withdrawalRequest.setBankName("Bank XYZ");
//         withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(1000));

//         Investor mockInvestor = new Investor();
//         mockInvestor.setId(1L);
//         mockInvestor.setDateOfBirth(LocalDate.of(1994, 8, 17)); 

//         Product mockProduct = new Product();
//         mockProduct.setId(2L);
//         mockProduct.setBalance(BigDecimal.valueOf(6000));
//         mockProduct.setInvestor(mockInvestor);
//         mockProduct.setType("savings");

//         when(productRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

//         // Assert
//         WithdrawalResponse withdrawalResponse = withdrawalService.createWithdrawal(withdrawalRequest);

//         assertNotNull(withdrawalResponse);
//         assertEquals(BigDecimal.valueOf(5000), withdrawalResponse.getNewBalance());
//         assertEquals(BigDecimal.valueOf(6000), withdrawalResponse.getPreviousBalance());
//         assertEquals(BigDecimal.valueOf(1000), withdrawalResponse.getWithdrawnBalance());
//     }

//     @Test
//     public void testCreateWithdrawal_InvalidWithdrawalAmount() {
//         // Mock data with an invalid withdrawal amount
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setAccountHolderDetails("John Doe");
//         withdrawalRequest.setBankName("Bank XYZ");
//         withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(4600)); // More than 90% of balance

//         Investor mockInvestor = new Investor();
//         mockInvestor.setId(1L);
//         mockInvestor.setDateOfBirth(LocalDate.of(1991, 8, 17)); 

//         Product mockProduct = new Product();
//         mockProduct.setId(2L);
//         mockProduct.setBalance(BigDecimal.valueOf(5000));
//         mockProduct.setInvestor(mockInvestor);
//         mockProduct.setType("savings");

//         when(productRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

//         // Test the method and expect an InvalidWithdrawalAmountException
//         assertThrows(InvalidWithdrawalAmountException.class, () -> withdrawalService.createWithdrawal(withdrawalRequest));
//     }

//     @Test
//     public void testCreateWithdrawal_InvalidWithdrawalRequest() {
//         // Mock data with missing withdrawal request data
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);

//         assertThrows(InvalidWithdrawalRequestException.class, () -> withdrawalService.createWithdrawal(withdrawalRequest));
//     }

//     @Test
//     public void testCreateWithdrawal_ProductNotFound() {
//         // Mock data with a non-existent product ID
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(999L);
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setAccountHolderDetails("John Doe");
//         withdrawalRequest.setBankName("Bank XYZ");
//         withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(4600));

//         when(productRepository.findById(999L)).thenReturn(Optional.empty());

//         // Test the method and expect a ProductNotAvailableException
//         assertThrows(ProductNotAvailableException.class, () -> withdrawalService.createWithdrawal(withdrawalRequest));
//     }


//     @Test
//     public void testCreateWithdrawal_RetirementProduct() {
//         // Mock data with a retirement product for an investor below 65
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setAccountHolderDetails("John Doe");
//         withdrawalRequest.setBankName("Bank XYZ");
//         withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(1000));

//         Investor mockInvestor = new Investor();
//         mockInvestor.setId(1L);
//         mockInvestor.setDateOfBirth(LocalDate.of(1994, 8, 17));

//         Product retirementProduct = new Product();
//         retirementProduct.setId(2L);
//         retirementProduct.setType("Retirement");
//         retirementProduct.setBalance(BigDecimal.valueOf(5000));
//         retirementProduct.setInvestor(mockInvestor);

//         when(productRepository.findById(2L)).thenReturn(Optional.of(retirementProduct));

//         // Test the method and expect a ProductNotAvailableException
//         assertThrows(ProductNotAvailableException.class, () -> withdrawalService.createWithdrawal(withdrawalRequest));
//     }

//     @Test
//     public void testCreateWithdrawal_RetirementProduct_When_Valid() {
//         // Mock data with a retirement product for an investor below 65
//         WithdrawalNoticeRequest withdrawalRequest = new WithdrawalNoticeRequest();
//         withdrawalRequest.setInvestorId(1L);
//         withdrawalRequest.setProductId(2L);
//         withdrawalRequest.setAccountNumber("123456789");
//         withdrawalRequest.setAccountHolderDetails("John Doe");
//         withdrawalRequest.setBankName("Bank XYZ");
//         withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(1000));

//         Investor mockInvestor = new Investor();
//         mockInvestor.setId(1L);
//         mockInvestor.setDateOfBirth(LocalDate.of(1916, 8, 17));

//         Product retirementProduct = new Product();
//         retirementProduct.setId(2L);
//         retirementProduct.setType("Retirement");
//         retirementProduct.setBalance(BigDecimal.valueOf(5000));
//         retirementProduct.setInvestor(mockInvestor);

//         when(productRepository.findById(2L)).thenReturn(Optional.of(retirementProduct));

//         WithdrawalResponse withdrawalResponse = withdrawalService.createWithdrawal(withdrawalRequest);

//         // Test the method and expect a ProductNotAvailableException
//         assertNotNull(withdrawalResponse);
//         assertEquals(BigDecimal.valueOf(4000), withdrawalResponse.getNewBalance());
//         assertEquals(BigDecimal.valueOf(5000), withdrawalResponse.getPreviousBalance());
//         assertEquals(BigDecimal.valueOf(1000), withdrawalResponse.getWithdrawnBalance());    }
    
// }

package com.enviro.assessment.grad001.bokangmakibinye.service;

import com.enviro.assessment.grad001.bokangmakibinye.exceptions.*;
import com.enviro.assessment.grad001.bokangmakibinye.model.*;
import com.enviro.assessment.grad001.bokangmakibinye.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WithdrawalServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    @InjectMocks
    private WithdrawalService withdrawalService;

    @Test
    public void testVerifyWithdrawalNoticeDetails_PositiveAmount() {
        //Arrange
        Investor investorMock = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Product product1Mock = createValidProduct("RT", "retirement", "1000000.0", 1L, investorMock);

        //Act
        product1Mock.setBalance(new BigDecimal("1000000.0"));
        investorMock.getProducts().add(product1Mock);
     
        //Assert
        when(productRepository.getReferenceById(1L)).thenReturn(product1Mock);
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal("1000.0"), LocalDate.of(2050,05,12));
        assertTrue(withdrawalService.verifyWithdrawalNoticeDetails(request));
    }

    @Test
    public void testVerifyWithdrawalNoticeDetails_NegativeAmount() {
        //Arrange
        Investor investorMock = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Product product1Mock = createValidProduct("RT", "retirement", "1000000.0", 1L, investorMock);

        //Act
        product1Mock.setBalance(new BigDecimal("1000000.0"));
        investorMock.getProducts().add(product1Mock);
     
        //Assert
        when(productRepository.getReferenceById(1L)).thenReturn(product1Mock);
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal("-1000.0"), LocalDate.of(2050,05,12));

        NegativeRequestAmountException exception = assertThrows(NegativeRequestAmountException.class,
                () -> withdrawalService.verifyWithdrawalNoticeDetails(request));
        assertEquals("Requested Amount cannot be a negative number.", exception.getMessage());
    }

    @Test
    public void testVerifyWithdrawalNoticeDetails_PastPayDate() {
        //Arrange
        Investor investorMock = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Product product1Mock = createValidProduct("RT", "retirement", "1000000.0", 1L, investorMock);

        //Act
        product1Mock.setBalance(new BigDecimal("1000000.0"));
        investorMock.getProducts().add(product1Mock);
     
        //Assert
        when(productRepository.getReferenceById(1L)).thenReturn(product1Mock);
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal("1000.0"), LocalDate.of(2000,05,12));
        DateCheckFailedException exception = assertThrows(DateCheckFailedException.class,
                () -> withdrawalService.verifyWithdrawalNoticeDetails(request));
        assertEquals("Requested Paydate cannot be in the past.", exception.getMessage());
    }

    @Test
    public void testVerifyWithdrawalNoticeDetails_ExceedsMaxAllowed() {
        //Arrange
        Investor investorMock = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Product product1Mock = createValidProduct("RT", "retirement", "1000.0", 1L, investorMock);

        //Act
 
        investorMock.getProducts().add(product1Mock);
     
        //Assert
        when(productRepository.getReferenceById(1L)).thenReturn(product1Mock);
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal("950.0"), LocalDate.of(2050,05,12));
        RequestedAmountExceedsMaxAllowedException exception = assertThrows(RequestedAmountExceedsMaxAllowedException.class,
                () -> withdrawalService.verifyWithdrawalNoticeDetails(request));
        assertEquals("Requested amount cannot be more than 90% of the current balance.", exception.getMessage());
    }

    @Test
    public void testCreateWithdrawal_Successful() {
        //Arrange
        String withdrawalAmount = "950.0";
        String productBalance = "1000.0";
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal(withdrawalAmount), LocalDate.of(2050,05,12));
        Investor investor = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Product product = createValidProduct("RT", "retirement", productBalance, 1L, investor);


        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        when(investorRepository.getReferenceById(anyLong())).thenReturn(investor);
        WithdrawalNoticeResponse response = withdrawalService.createWithdrawal(request);

        //Assert
        assertNotNull(response);
        assertEquals(new BigDecimal(withdrawalAmount), response.getWithdrawnBalance());
        assertEquals(new BigDecimal(productBalance).subtract(new BigDecimal(withdrawalAmount)), response.getNewBalance());
        
    }

    @Test
    public void testCreateWithdrawal_InvestorIdMismatch() {
        String withdrawalAmount = "950.0";
        String productBalance = "1000.0";
        WithdrawalNoticeRequest request = createValidWithdrawalNoticeRequest(1L, 1L, new BigDecimal(withdrawalAmount), LocalDate.of(2050,05,12));
        Investor investor1 = createValidInvestor(LocalDate.of(1994, 8, 17), "Bokang", 1L, "Makibs", new ArrayList<Product>());
        Investor investor2 = createValidInvestor(LocalDate.of(1999, 3, 24), "Tshepo", 5L, "Mathe", new ArrayList<Product>());
        Product product = createValidProduct("RT", "retirement", productBalance, 1L, investor2);

        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        when(investorRepository.getReferenceById(anyLong())).thenReturn(investor1);

        InvestorIdMismatchException exception = assertThrows(InvestorIdMismatchException.class,
                () -> withdrawalService.createWithdrawal(request));
        assertEquals("Investor doesn't own product with id: " + product.getId(), exception.getMessage());
    }

    private WithdrawalNoticeRequest createValidWithdrawalNoticeRequest(Long investorId, Long productId, BigDecimal requestedAmnt, LocalDate paydate) {
        WithdrawalNoticeRequest request = new WithdrawalNoticeRequest();
        request.setAccountHolderName("John Doe");
        request.setAccountNumber("123456789");
        request.setInvestorId(investorId);
        request.setProductId(productId);
        request.setRequestedAmount(requestedAmnt);
        request.setRequestedPaydate(paydate);
        // request.setNoticeCreationDate(null);
        return request;
    }

    private Product createValidProduct(String productType, String productName, String productAmnt, Long id, Investor investor) {
        Product product = new Product();
        product.setBalance(new BigDecimal(productAmnt));
        product.setId(id);
        product.setType(productType);
        product.setProductName(productName);
        product.setInvestor(investor);
        return product;
    }

    private Investor createValidInvestor(LocalDate dob, String fName, Long id, String lName, List<Product> products) {
        Investor investor = new Investor();
        investor.setAddress("XYZ");
        investor.setContact("wuyy");
        investor.setDateOfBirth(dob);
        investor.setFirstName(fName);
        investor.setId(id);
        investor.setLastName(lName);
        investor.setProducts(products);
        return investor;
    }

    private List<WithdrawalNotice> createValidWithdrawalNotices(Investor investor, Product product1, Product product2) {
        WithdrawalNotice withdrawalNotice1 = new WithdrawalNotice();
        withdrawalNotice1.setInvestor(investor);
        withdrawalNotice1.setAccountHolderName("John Doe");
        withdrawalNotice1.setAccountNumber("123456789");
        withdrawalNotice1.setId(1L);
        withdrawalNotice1.setNoticeCreationDate(LocalDate.of(2023, 12, 06));
        withdrawalNotice1.setRequestedPaydate(LocalDate.of(2024, 03, 03));
        withdrawalNotice1.setProduct(product1);
        withdrawalNotice1.setRequestedAmount(new BigDecimal("1000.0"));

        WithdrawalNotice withdrawalNotice2 = new WithdrawalNotice();
        withdrawalNotice2.setInvestor(investor);
        withdrawalNotice1.setAccountHolderName("John Doe");
        withdrawalNotice1.setAccountNumber("123456789");
        withdrawalNotice1.setId(2L);
        withdrawalNotice1.setNoticeCreationDate(LocalDate.of(2023, 12, 06));
        withdrawalNotice1.setRequestedPaydate(LocalDate.of(2024, 03, 03));
        withdrawalNotice1.setProduct(product2);
        withdrawalNotice1.setRequestedAmount(new BigDecimal("1000.0"));

        List<WithdrawalNotice> withdrawalNotices = new ArrayList<>();
        withdrawalNotices.add(withdrawalNotice1);
        withdrawalNotices.add(withdrawalNotice2);
        return withdrawalNotices;
    }
}


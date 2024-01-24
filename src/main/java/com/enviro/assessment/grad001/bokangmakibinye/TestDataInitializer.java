package com.enviro.assessment.grad001.bokangmakibinye;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.enviro.assessment.grad001.bokangmakibinye.model.Investor;
import com.enviro.assessment.grad001.bokangmakibinye.model.Product;
import com.enviro.assessment.grad001.bokangmakibinye.repository.InvestorRepository;
import com.enviro.assessment.grad001.bokangmakibinye.repository.ProductRepository;


@Component
public class TestDataInitializer implements CommandLineRunner{

    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TestDataInitializer(InvestorRepository investorRepository, ProductRepository productRepository) {
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Investor investor1 = new Investor();
        Investor investor2 = new Investor();
        Investor investor3 = new Investor();
        Investor investor4 = new Investor();

        investor1.setFirstName("Bokang");
        investor1.setLastName("Makibinye");
        investor1.setAddress("36 van beek, Johannesburg, 2094");
        investor1.setContact("bomakib022@student.wethinkcode.co.za");
        investor1.setDateOfBirth(LocalDate.of(1994,8,17));

        Product retirement1 = new Product();
        retirement1.setBalance(new BigDecimal(100000.0));
        retirement1.setProductName("Retirement plan");
        retirement1.setType("RETIREMENT");
        retirement1.setInvestor(investor1);

        Product savings1 = new Product();
        savings1.setBalance(new BigDecimal(36000.0));
        savings1.setProductName("Savings");
        savings1.setType("SAVINGS");
        savings1.setInvestor(investor1);

        investor2.setFirstName("Matlhatsi");
        investor2.setLastName("Mohale");
        investor2.setAddress("08 Pretorius, Tshwane, 1009");
        investor2.setContact("mamohale022@student.wethinkcode.co.za");
        investor2.setDateOfBirth(LocalDate.of(1994,8,17));

        Product savings2 = new Product();
        savings2.setBalance(new BigDecimal(64000.0));
        savings2.setProductName("Savings");
        savings2.setType("SAVINGS");
        savings2.setInvestor(investor2);

        investor3.setFirstName("Sam");
        investor3.setLastName("Smith");
        investor3.setAddress("09 Oloff, Randburg, 2000");
        investor3.setContact("samsmith022@student.wethinkcode.co.za");
        investor3.setDateOfBirth(LocalDate.of(1994,8,17));

        Product savings3 = new Product();
        savings3.setBalance(new BigDecimal(49500.0));
        savings3.setProductName("Savings");
        savings3.setType("SAVINGS");
        savings3.setInvestor(investor3);

        investor4.setFirstName("Tatenda");
        investor4.setLastName("Beni");
        investor4.setAddress("234 Something street, Bryanston, 2050");
        investor4.setContact("tabeni022@student.wethinkcode.co.za");
        investor4.setDateOfBirth(LocalDate.of(1994,8,17));

        Product retirement2 = new Product();
        retirement2.setBalance(new BigDecimal(100000.0));
        retirement2.setProductName("Retirement plan");
        retirement2.setType("RETIREMENT");
        retirement2.setInvestor(investor4);


        investorRepository.saveAll(Arrays.asList(investor1, investor2, investor3, investor4));
        productRepository.saveAll(Arrays.asList(retirement1, retirement2, savings1, savings2, savings3));
    }
    
}

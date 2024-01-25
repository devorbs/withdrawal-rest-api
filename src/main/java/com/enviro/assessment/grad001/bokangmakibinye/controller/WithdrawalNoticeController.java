package com.enviro.assessment.grad001.bokangmakibinye.controller;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNotice;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeCsvDTO;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeRequest;
import com.enviro.assessment.grad001.bokangmakibinye.model.WithdrawalNoticeResponse;
import com.enviro.assessment.grad001.bokangmakibinye.service.WithdrawalService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;



/**
 * Controller class for handling withdrawal-related HTTP requests.
 */
@RestController
@RequestMapping("/withdrawal")
public class WithdrawalNoticeController {
    
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalNoticeController.class);

    private WithdrawalService withdrawalService;

    /**
     * Constructs a new WithdrawalNoticeController with the specified withdrawal service.
     *
     * @param withdrawalService The service responsible for handling withdrawal operations.
     */
    @Autowired
    public WithdrawalNoticeController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    /**
     * Creates a withdrawal notice based on the provided withdrawal request.
     *
     * @param withdrawalRequest The withdrawal request.
     * @return ResponseEntity containing the withdrawal response or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createWithdrawalNotice(@RequestBody WithdrawalNoticeRequest withdrawalNoticeRequest) {
        // System.out.println(withdrawalNoticeRequest);
        WithdrawalNoticeResponse response;
        if(withdrawalService.verifyWithdrawalNoticeDetails(withdrawalNoticeRequest)) {
            response = withdrawalService.createWithdrawal(withdrawalNoticeRequest);
            if (response != null) {
                return ResponseEntity.ok().body(response);
            }
        }

        return new ResponseEntity<String>("Error parsing withdrawal request values.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/export/{investorId}")
    public void exportCsv(HttpServletResponse response, @PathVariable Long investorId) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String fileName = "withdrawal-statement.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"");

        List<WithdrawalNoticeCsvDTO> withdrawalNotices = withdrawalService.downloadWithdrawalNotices(investorId);
        StatefulBeanToCsv<WithdrawalNoticeCsvDTO> writer = new StatefulBeanToCsvBuilder<WithdrawalNoticeCsvDTO>(response.getWriter())
                                                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                                                    .withOrderedResults(false)
                                                    .build();

        writer.write(withdrawalNotices);
    }
}

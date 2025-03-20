package org.example.dockerdbexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.dockerdbexample.entity.Receipt;
import org.example.dockerdbexample.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receipt")
@AllArgsConstructor
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptService receiptService;

    @Operation(summary = "Получить информацию о всех чеках")
    @GetMapping("/get-all-receipt")
    public ResponseEntity<List<Receipt>> getAll() {
        logger.info("Запрос на получение всех чеков");

        try {
            List<Receipt> receipts = receiptService.getAllReceipts();
            logger.info("Возвращено чеков: {}", receipts.size());
            return ResponseEntity.ok(receipts);
        } catch (Exception e) {
            logger.error("Ошибка при получении всех чеков: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Возвращает информацию о чеках пользователя")
    @GetMapping("/get-receipt")
    public ResponseEntity<List<Receipt>> getReceiptById() {
        logger.info("Запрос на получение чеков пользователя");

        try {
            List<Receipt> receipts = receiptService.generateListReceipt();
            logger.info("Пользователь получил {} чеков", receipts.size());
            return ResponseEntity.ok(receipts);
        } catch (Exception e) {
            logger.error("Ошибка при получении чеков пользователя: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
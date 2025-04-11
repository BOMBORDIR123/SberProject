package org.example.dockerdbexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.dockerdbexample.entity.PromoCode;
import org.example.dockerdbexample.service.PromoCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/promoCode")
@AllArgsConstructor
public class PromoCodeController {

    private static final Logger logger = LoggerFactory.getLogger(PromoCodeController.class);
    private final PromoCodeService promoCodeService;

    @Operation(summary = "Получить информацию о покупке промокода")
    @PostMapping("/buy")
    public ResponseEntity<PromoCode> buyPromo(@RequestParam("companyName") String companyName,
                                              @RequestParam("discountPercentage") Integer discountPercentage) {
        logger.info("Запрос на покупку промокода: companyName={}, discountPercentage={}", companyName, discountPercentage);

        PromoCode promoCode = promoCodeService.buyPromo(companyName, discountPercentage);

        logger.info("Промокод куплен: {}", promoCode);
        return new ResponseEntity<>(promoCode, HttpStatus.OK);
    }
}
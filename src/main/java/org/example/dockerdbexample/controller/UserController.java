package org.example.dockerdbexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.dto.user.UserRegistrationDto;
import org.example.dockerdbexample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Operation(summary = "Получить информацию о топе пользователей")
    @GetMapping("top")
    public ResponseEntity<List<UserRegistrationDto>> topUser(){
        logger.info("Запрос на получение топ-10 пользователей");

        List<UserRegistrationDto> topUsers = userService.topUsers();

        logger.info("Отправлено {} пользователей в топе", topUsers.size());
        return new ResponseEntity<>(topUsers, HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        logger.info("Запрос на получение текущего пользователя");

        // Получаем текущую аутентификацию из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String number = authentication.getName();

        logger.info("Пользователь аутентифицирован: {}", number);

        try {
            UserDto user = userService.findByPhoneNumber(number);
            logger.info("Найден пользователь: {} {}", user.getFirstName(), user.getLastName());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Ошибка при получении пользователя с номером {}: {}", number, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

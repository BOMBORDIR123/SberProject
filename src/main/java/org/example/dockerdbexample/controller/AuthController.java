package org.example.dockerdbexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.dockerdbexample.dto.auth.UserAuthDto;
import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.dto.user.UserRegistrationDto;
import org.example.dockerdbexample.entity.User;
import org.example.dockerdbexample.exceptions.IncorrectData;
import org.example.dockerdbexample.repository.UserRepository;
import org.example.dockerdbexample.security.jwt.JwtService;
import org.example.dockerdbexample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserRepository userRepository;
    private UserService userService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Operation(summary = "Вход в аккаунт")
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserAuthDto userCredentialDto , HttpServletResponse response) {
        // 1. Ищем пользователя по номеру
        String number = userCredentialDto.getPhoneNumber();
        String password = userCredentialDto.getPassword();
        logger.info("Попытка входа пользователя с номером телефона: {}", number);

        Optional<User> userOpt = userRepository.findByPhoneNumber(number);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Успешный вход пользователя с номером телефона: {}", number);

                // 3. Генерируем JWT с ролями
                String jwtToken = jwtService.generateJwtToken(user.getPhoneNumber(), user.getRoles());

                // 4. Создаем httpOnly cookie
                Cookie jwtCookie = new Cookie("__Host-auth-token", jwtToken);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(true); // Для работы через HTTPS, можешь отключить в dev-окружении
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(180 * 24 * 60 * 60); // Время жизни куки - 180 дней

                // 5. Добавляем куки в response
                response.addCookie(jwtCookie);
                UserDto dto = userService.toDto(user);

                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
            else logger.warn("Неудачная попытка входа. Неверный пароль для пользователя с номером телефона: {}", number);
        }
        logger.warn("Неудачная попытка входа. Пользователь с номером телефона {} не найден.", number);

        // 6. Если аутентификация неуспешна, возвращаем ошибку
        throw new IncorrectData();
    }

    @Operation(summary = "Регистрация в аккаунт")
    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationDto> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        logger.info("Регистрация нового пользователя с номером телефона: {}", userRegistrationDto.getPhoneNumber());

        UserRegistrationDto registeredUser = userService.addUser(userRegistrationDto);

        logger.info("Пользователь с номером телефона {} успешно зарегистрирован.", userRegistrationDto.getPhoneNumber());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}

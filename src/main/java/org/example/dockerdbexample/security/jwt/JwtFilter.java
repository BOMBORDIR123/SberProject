package org.example.dockerdbexample.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dockerdbexample.security.CustomUserDetails;
import org.example.dockerdbexample.security.CustomUserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Извлечение JWT токена из куки
        String jwtToken = extractJwtFromCookies(request);
        log.debug("Попытка извлечь JWT токен из куки...");

        // Проверка, есть ли токен и не авторизован ли уже пользователь
        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Валидация токена
                if (jwtService.validateJwtToken(jwtToken)) {
                    // Извлечение номера телефона из токена
                    String phoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);  // Измените метод для получения телефона
                    log.debug("Токен валиден, извлечён номер телефона: {}", phoneNumber);

                    // Загрузка пользователя из базы данных
                    CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(phoneNumber);

                    // Создание объекта для Spring Security
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    customUserDetails, null, customUserDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Установка авторизации в SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Пользователь {} успешно аутентифицирован.", phoneNumber);
                } else {
                    log.warn("Невалидный JWT токен.");
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке JWT токена: {}", e.getMessage(), e);
            }
        }

        // Продолжение цепочки фильтров
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "auth-token".equals(cookie.getName()))  // Убедитесь, что куки имеют это имя
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}

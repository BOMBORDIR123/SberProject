package org.example.dockerdbexample.service;

import lombok.AllArgsConstructor;
import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.entity.PromoCode;
import org.example.dockerdbexample.exceptions.InsufficientBalance;
import org.example.dockerdbexample.repository.PromoCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PromoCodeService.class);

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Символы для генерации
    private static final int CODE_LENGTH = 8;



    public PromoCode buyPromo(String companyName, Integer bonus) {
        // Получаем текущую аутентификацию из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Получаем номер (или имя пользователя) из Authentication
        String number = authentication.getName();
        UserDto currentUser = userService.findByPhoneNumber(number);

        logger.info("Пользователь {} пытается купить купон на {} за {} бонусов",
                number, companyName, bonus);

        if (currentUser.getBonus() >= bonus) {
            currentUser.setBonus(currentUser.getBonus() - bonus); // Обновляем бонусы пользователя
            PromoCode promoCode = new PromoCode();
            promoCode.setCode(generatePromoCode());
            promoCode.setCompanyName(companyName);
            promoCode.setDiscountPercentage(bonus);
            promoCode.setUser(userService.toUser(currentUser));
            PromoCode save = promoCodeRepository.save(promoCode);
            promoCode.setId(save.getId());
            currentUser.getPromoCodes().add(promoCode);
            userService.updateUser(currentUser);

            logger.info("Купон {} успешно создан для компании {}", promoCode, companyName);

            return promoCode;
        }
        else {
            logger.warn("Недостаточно бонусов у пользователя {}. Доступно: {}, Требуется: {}",
                    number, currentUser.getBonus(), bonus);
            throw new InsufficientBalance();
        }
    }

    public String generatePromoCode() {

        logger.info("Начало генерации промокода");

        SecureRandom random = new SecureRandom();
        StringBuilder promoCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            promoCode.append(CHARACTERS.charAt(index));
        }

        String generatedCode = promoCode.toString();
        logger.info("Сгенерирован промокод: {}", generatedCode);

        return promoCode.toString();
    }

}



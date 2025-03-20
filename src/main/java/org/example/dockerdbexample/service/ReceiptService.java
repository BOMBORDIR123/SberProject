package org.example.dockerdbexample.service;

import lombok.AllArgsConstructor;
import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.entity.PurchaseItem;
import org.example.dockerdbexample.entity.Receipt;
import org.example.dockerdbexample.repository.PurchaseItemRepository;
import org.example.dockerdbexample.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ReceiptService {
    private ReceiptRepository repository;
    private final ReceiptRepository receiptRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final Random random = new Random();
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    public List<Receipt> getAllReceipts() {
        return repository.findAll();
    }

    public Receipt generateRandomReceipt() {

        logger.info("Начало генерации случайного чека");

        List<PurchaseItem> allItems = purchaseItemRepository.findAll();

        if (allItems.isEmpty()) {
            logger.warn("Нет доступных товаров для генерации чека!");
            throw new RuntimeException("Нет товаров");
        }

        // Генерируем случайное количество товаров (от 1 до 8)
        int itemCount = 1 + random.nextInt(8);
        List<PurchaseItem> selectedItems = getRandomItems(allItems, itemCount);
        selectedItems = selectedItems.stream().distinct().toList();

        logger.debug("Выбрано товаров: {}", selectedItems.size());

        // Проверяем, есть ли среди выбранных товаров "Транспорт"
        boolean hasTransport = selectedItems.stream()
                .anyMatch(item -> "Транспорт".equalsIgnoreCase(item.getCategory()));

        if (hasTransport) {
            logger.info("Чек содержит транспортные услуги");
        }

        // Если есть "Транспорт", создаем чек только с этой услугой
        List<PurchaseItem> receiptItems;
        if (hasTransport) {
            receiptItems = selectedItems.stream()
                    .filter(item -> "Транспорт".equalsIgnoreCase(item.getCategory()))
                    .limit(1)  // Ограничиваем до одной услуги "Транспорт"
                    .toList();

            // Устанавливаем количество для транспортной услуги как null
            receiptItems.forEach(item -> item.setQuantity(1));
        } else {
            // Иначе добавляем все выбранные товары с случайным количеством
            receiptItems = new ArrayList<>();
            for (PurchaseItem item : selectedItems) {
                // Устанавливаем случайное количество для обычных товаров
                item.setQuantity(1 + random.nextInt(4));
                receiptItems.add(item);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        UserDto byPhoneNumber = userService.findByPhoneNumber(phoneNumber);


        // Создаем чек с выбранными товарами или услугой
        Receipt receipt = new Receipt();
        receipt.setItems(receiptItems);
        receipt.setUser(userService.toUser(byPhoneNumber));
        receipt.setBonus(calculateBonus(receipt));
        receipt.setCarbonFootprint(calculateCarbonFootprint(receipt));
        receipt.setDate(String.valueOf(getRandomDateInRange()));
        //Установка баланса у юзера
        byPhoneNumber.setBonus(byPhoneNumber.getBonus() + receipt.getBonus());
        byPhoneNumber.setFullBonus(byPhoneNumber.getFullBonus() + receipt.getBonus());
        // Округление
        Double updateCarbon = byPhoneNumber.getCarbonFootprint() + receipt.getCarbonFootprint();
        BigDecimal roundedCarbon = BigDecimal.valueOf(updateCarbon).setScale(2, RoundingMode.HALF_UP);
        updateCarbon = roundedCarbon.doubleValue(); // Получаем округленное значение как Double
        byPhoneNumber.setCarbonFootprint(updateCarbon);
        userService.updateUser(byPhoneNumber);

        logger.info("Чек создан. Бонус: {}, Углеродный след: {}",
                receipt.getBonus(), receipt.getCarbonFootprint());

        return receiptRepository.save(receipt);
    }

    public List<Receipt> generateListReceipt(){

        logger.info("Начало генерации списка чеков");

        List<Receipt> receiptList = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            receiptList.add(generateRandomReceipt());

            logger.debug("Сгенерирован чек №{}: {}", i + 1, receiptList.size());
        }

        logger.info("Список чеков успешно сгенерирован. Всего чеков: {}", receiptList.size());

        return receiptList;
    }

    // Метод для случайного выбора товаров из списка
    private List<PurchaseItem> getRandomItems(List<PurchaseItem> items, int count) {

        logger.info("Выбор {} случайных товаров из списка", count);

        List<PurchaseItem> selectedItems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PurchaseItem item = items.get(random.nextInt(items.size()));
            selectedItems.add(item);
            logger.debug("Добавлен товар: {}", item);
        }

        logger.info("Выбрано {} товаров", selectedItems.size());

        return selectedItems;
    }

    private int calculateBonus(Receipt receipt) {
        logger.info("Начало расчета бонусов для чека {}", receipt);

        int sum = receipt.getItems().stream()
                .mapToInt(item -> {
                    int bonus = Optional.ofNullable(item.getBonus()).orElse(0);
                    int quantity = Optional.ofNullable(item.getQuantity()).orElse(1);
                    logger.debug("Товар: {}, бонус: {}, количество: {}", item.getName(), bonus, quantity);
                    return bonus * quantity;
                })
                .sum();
        receipt.setBonus(sum);

        logger.info("Расчет бонусов завершен. Итоговая сумма: {}", sum);

        return sum;
    }

    private Double calculateCarbonFootprint(Receipt receipt) {
        logger.info("Расчет углеродного следа для чека {}", receipt);

        double totalCarbonFootprint = receipt.getItems().stream()
                .mapToDouble(item -> {
                    double carbon = Optional.ofNullable(item.getCarbonFootprint()).orElse(0.0);
                    int quantity = Optional.ofNullable(item.getQuantity()).orElse(1);
                    logger.debug("Товар: {}, углеродный след: {}, количество: {}", item.getName(), carbon, quantity);
                    return carbon * quantity;
                })
                .sum();

        // Округляем до двух знаков после запятой
        BigDecimal roundedFootprint = new BigDecimal(totalCarbonFootprint).setScale(2, RoundingMode.HALF_UP);

        double finalCarbonFootprint = roundedFootprint.doubleValue();
        receipt.setCarbonFootprint(finalCarbonFootprint);

        logger.info("Углеродный след рассчитан: {}", finalCarbonFootprint);
        return finalCarbonFootprint;
    }

    public LocalDate getRandomDateInRange() {
        logger.info("Генерация случайной даты за последний год");

        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);

        // Генерируем случайное количество дней в диапазоне от 0 до 365
        long daysBetween = ChronoUnit.DAYS.between(oneYearAgo, today);
        long randomDays = random.nextInt((int) daysBetween + 1); // +1, чтобы включить последний день

        // Получаем случайную дату
        LocalDate randomDate = oneYearAgo.plusDays(randomDays);
        logger.info("Сгенерирована дата: {}", randomDate);

        return randomDate;
    }
}

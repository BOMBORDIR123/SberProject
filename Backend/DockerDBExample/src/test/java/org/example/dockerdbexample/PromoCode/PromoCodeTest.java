package org.example.dockerdbexample.PromoCode;

import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.entity.PurchaseItem;
import org.example.dockerdbexample.entity.Receipt;
import org.example.dockerdbexample.repository.PurchaseItemRepository;
import org.example.dockerdbexample.repository.ReceiptRepository;
import org.example.dockerdbexample.service.ReceiptService;
import org.example.dockerdbexample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptCodeTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private PurchaseItemRepository purchaseItemRepository;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ReceiptService receiptService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setPhoneNumber("1234567890");
        userDto.setBonus(0);
        userDto.setFullBonus(0);
        userDto.setCarbonFootprint(0.0);
    }

    @Test
    void getAllReceipts_ShouldReturnList() {
        when(receiptRepository.findAll()).thenReturn(List.of(new Receipt(), new Receipt()));
        List<Receipt> receipts = receiptService.getAllReceipts();
        assertEquals(2, receipts.size());
    }

    @Test
    void generateRandomReceipt_ShouldCreateReceipt() {
        when(purchaseItemRepository.findAll()).thenReturn(List.of(new PurchaseItem()));
        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1234567890");
        when(userService.findByPhoneNumber("1234567890")).thenReturn(userDto);

        SecurityContextHolder.setContext(securityContext);
        Receipt receipt = receiptService.generateRandomReceipt();
        assertNotNull(receipt);
    }

    @Test
    void generateListReceipt_ShouldCreateMultipleReceipts() {
        when(purchaseItemRepository.findAll()).thenReturn(List.of(new PurchaseItem()));
        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1234567890");
        when(userService.findByPhoneNumber("1234567890")).thenReturn(userDto);

        SecurityContextHolder.setContext(securityContext);
        List<Receipt> receipts = receiptService.generateListReceipt();
        assertEquals(30, receipts.size());
    }

    @Test
    void getRandomDateInRange_ShouldReturnValidDate() {
        LocalDate randomDate = receiptService.getRandomDateInRange();
        assertNotNull(randomDate);
        assertTrue(randomDate.isAfter(LocalDate.now().minusYears(1)) && randomDate.isBefore(LocalDate.now().plusDays(1)));
    }
}


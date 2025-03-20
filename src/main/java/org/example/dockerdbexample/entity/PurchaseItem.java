package org.example.dockerdbexample.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "purchase_items")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Название товара или услуги
    private String category; // Категория (продукты питания, транспорт и т.д.)
    private Integer bonus; // Бонус за экологичные товары
    private Double carbonFootprint; // Количество углеродного следа у товара
    private Boolean isEcoFriendly; // Экологичность товара
    @Column(nullable = true)
    private Integer quantity; // Количество продукта в чеке

    @Column(nullable = true)
    private String recommendation; // Рекомендация, если товар не является экологичным

    @ManyToMany(mappedBy = "items")  // Обратная сторона связи
    @JsonBackReference // Указываем, что это "обратная" сторона
    private List<Receipt> receipts;  // Список чеков, в которых присутствует товар
}

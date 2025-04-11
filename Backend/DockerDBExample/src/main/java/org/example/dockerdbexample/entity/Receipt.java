package org.example.dockerdbexample.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double carbonFootprint; // Общий углеродный след у чека

    private Integer bonus;  // Бонус за чек

    private String date; // Дата покупки

    @ManyToOne  // Связь с классом User
    @JoinColumn(name = "user_id")
    @JsonBackReference // Указываем, что это "обратная" сторона
    private User user;  // Владелец чека

    @ManyToMany  // Изменено на ManyToMany
    @JoinTable(
            name = "receipt_purchase_items",
            joinColumns = @JoinColumn(name = "receipt_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_item_id")
    )
    @JsonManagedReference // Указываем, что это "управляющая" сторона
    private List<PurchaseItem> items;  // Список товаров в чеке
}

package org.example.dockerdbexample.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "promo_codes")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName; // Название компании

    private String code; // Сам промокод

    private Integer discountPercentage; // Процент скидки

    @ManyToOne
    @JoinColumn(name = "user_id") // Указываем имя столбца в базе данных
    @JsonBackReference // Указываем, что это "обратная" сторона

    private User user;
}

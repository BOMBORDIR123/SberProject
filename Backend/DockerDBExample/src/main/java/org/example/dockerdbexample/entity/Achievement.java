package org.example.dockerdbexample.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Название достижения

    private String description; // Описание достижения

    @Column(nullable = false)
    private Integer points; // Очки или бонусы, начисляемые за достижение

    // Географические координаты
    private Double targetLatitude; // Целевая широта
    private Double targetLongitude; // Целевая долгота
    private Double targetRadius; // Радиус в метрах для выполнения задания

    // URL или имя файла изображения, подтверждающего выполнение
    private Byte photoUrl;

    // Связь с пользователем
    private Boolean isAccept;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

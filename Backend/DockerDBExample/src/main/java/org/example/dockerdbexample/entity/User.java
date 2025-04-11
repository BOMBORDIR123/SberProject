package org.example.dockerdbexample.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    public Integer fullBonus;

    private String password;

    private String firstName;

    private String lastName;

    private Integer bonus;

    private Double carbonFootprint; // Общий углеродный след у чека


    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles; // Роли пользователя

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Указываем, что это "управляющая" сторона
    private List<Receipt> receipts;  // Список чеков, для отслеживания углеродного следа

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Achievement> achievements; // Награды и достижения

    // Связь с промокодами
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference // Указываем, что это "управляющая" сторона
    private List<PromoCode> promoCodes;
}

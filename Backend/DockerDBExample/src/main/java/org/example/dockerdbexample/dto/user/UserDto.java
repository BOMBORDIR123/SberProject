package org.example.dockerdbexample.dto.user;


import lombok.Data;
import org.example.dockerdbexample.entity.Achievement;
import org.example.dockerdbexample.entity.PromoCode;
import org.example.dockerdbexample.entity.Receipt;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;


    private String phoneNumber;

    private String password;

    private String firstName;

    private String lastName;

    public Integer fullBonus;

    private Integer bonus;

    private Double carbonFootprint; // Общий углеродный след у чека

    private Set<String> roles;

    private List<Receipt> receipts;  // Список чеков, для отслеживания углеродного следа

    private List<Achievement> achievements; // Награды и достижения

    private List<PromoCode> promoCodes;


}

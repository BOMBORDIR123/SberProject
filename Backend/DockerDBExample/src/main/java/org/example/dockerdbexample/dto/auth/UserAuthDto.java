package org.example.dockerdbexample.dto.auth;

import lombok.Data;

@Data
public class UserAuthDto {
    private String phoneNumber;
    private String password;
}

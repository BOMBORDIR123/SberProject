package org.example.dockerdbexample.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.dto.user.UserRegistrationDto;
import org.example.dockerdbexample.entity.User;
import org.example.dockerdbexample.exceptions.DifferentPassword;
import org.example.dockerdbexample.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto findByPhoneNumber(String number) {
        logger.info("Поиск пользователя по номеру телефона: {}", number);

        User user = userRepository.findByPhoneNumber(number).orElseThrow(() -> {
            logger.warn("Пользователь с номером {} не найден", number);
            return new UsernameNotFoundException("User with phone number " + number + " not found");
        });

        logger.info("Пользователь найден: {}", user.getPhoneNumber());
        return toDto(user);
    }

    //Регистрация нового пользователя
    @Transactional
    public UserRegistrationDto addUser(UserRegistrationDto userDto) {
        logger.info("Регистрация нового пользователя: {}", userDto.getPhoneNumber());

        if (userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent()) {
            logger.error("Пользователь с номером {} уже существует", userDto.getPhoneNumber());
            throw new RuntimeException("User with phone number " + userDto.getPhoneNumber() + " already exists");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            logger.error("Пароли не совпадают для пользователя: {}", userDto.getPhoneNumber());
            throw new DifferentPassword();
        }

        User entity = RegistrationDtotoUser(userDto);
        if (entity == null) {
            logger.error("Ошибка преобразования DTO в User entity");
            throw new RuntimeException("Mapped entity is null");
        }

        entity.setRoles(Set.of("USER"));
        entity.setBonus(0);
        entity.setFullBonus(0);
        entity.setCarbonFootprint(0D);
        entity.setPassword(passwordEncoder.encode(entity.getPassword())); // Хеширование пароля

        User currentUser = userRepository.save(entity);
        logger.info("Пользователь успешно зарегистрирован: {}", currentUser.getPhoneNumber());

        return toUserRegistrationDto(currentUser);
    }

    public List<UserRegistrationDto> topUsers() {
        logger.info("Получение списка топ-10 пользователей");

        List<UserRegistrationDto> topUsers = userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getFullBonus).reversed())
                .limit(10)
                .map(this::toUserRegistrationDto)
                .collect(Collectors.toList());

        logger.info("Топ-10 пользователей загружены ({} пользователей)", topUsers.size());
        return topUsers;
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        logger.info("Обновление данных пользователя: {}", userDto.getPhoneNumber());

        UserDto updatedUser = toDto(userRepository.save(toUser(userDto)));
        logger.info("Пользователь {} обновлен", updatedUser.getPhoneNumber());

        return updatedUser;
    }


    public User RegistrationDtotoUser(UserRegistrationDto userDto){
        logger.debug("Преобразование UserRegistrationDto в User");

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(userDto.getRoles());
        user.setId(userDto.getId());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());

        logger.debug("Преобразование успешно: {}", user.getPhoneNumber());
        return  user;
    }

    public UserRegistrationDto toUserRegistrationDto(User user){
        logger.debug("Преобразование User в UserRegistrationDto");

        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRoles(user.getRoles());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());

        logger.debug("Преобразование успешно: {}", userDto.getPhoneNumber());
        return userDto;
    }

    public UserDto toDto(User user){
        logger.debug("Преобразование User в UserDto");

        UserDto userDto = new UserDto();
        userDto.setBonus(user.getBonus());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRoles(user.getRoles());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setAchievements(user.getAchievements());
        userDto.setReceipts(user.getReceipts());
        userDto.setCarbonFootprint(user.getCarbonFootprint());
        userDto.setPromoCodes(user.getPromoCodes());
        userDto.setFullBonus(user.getFullBonus());

        logger.debug("Преобразование успешно: {}", userDto.getPhoneNumber());
        return userDto;
    }

    public User toUser(UserDto userDto){
        logger.debug("Преобразование UserDto в User");

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(userDto.getRoles());
        user.setId(userDto.getId());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setBonus(userDto.getBonus());
        user.setAchievements(userDto.getAchievements());
        user.setReceipts(userDto.getReceipts());
        user.setCarbonFootprint(userDto.getCarbonFootprint());
        user.setPromoCodes(userDto.getPromoCodes());
        user.setFullBonus(userDto.getFullBonus());

        logger.debug("Преобразование успешно: {}", user.getPhoneNumber());
        return  user;
    }
}

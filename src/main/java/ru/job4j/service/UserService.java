package ru.job4j.service;

import lombok.AllArgsConstructor;
import ru.job4j.repository.UserRepository;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}

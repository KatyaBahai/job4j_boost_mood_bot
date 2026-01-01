package ru.job4j.bmb.service;

import lombok.AllArgsConstructor;
import ru.job4j.bmb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}

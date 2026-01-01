package ru.job4j.bmb.service;

import lombok.AllArgsConstructor;
import ru.job4j.bmb.repository.AchievementRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class
AchievementService {
    private final AchievementRepository achievementRepository;
}

package ru.job4j.service;

import lombok.AllArgsConstructor;
import ru.job4j.repository.AchievementRepository;

@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
}

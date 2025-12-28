package ru.job4j.service;

import lombok.AllArgsConstructor;
import ru.job4j.recommendation.RecommendationEngine;
import ru.job4j.repository.MoodLogRepository;

@AllArgsConstructor
public class MoodService {
    private final RecommendationEngine recommendationEngine;
    private final MoodLogRepository moodLogRepository;
}

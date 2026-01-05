package ru.job4j.bmb.service;

import lombok.AllArgsConstructor;
import ru.job4j.bmb.recommendation.RecommendationEngine;
import ru.job4j.bmb.repository.MoodLogRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoodService {
    private final RecommendationEngine recommendationEngine;
    private final MoodLogRepository moodLogRepository;
}

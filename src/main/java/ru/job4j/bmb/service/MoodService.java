package ru.job4j.bmb.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import ru.job4j.bmb.recommendation.RecommendationEngine;
import ru.job4j.bmb.repository.MoodLogRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoodService {
    private final RecommendationEngine recommendationEngine;
    private final MoodLogRepository moodLogRepository;

    @PostConstruct
    public void init() {
        System.out.println("The bean is going through init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("The bean is going to be destroyed now.");
    }
}

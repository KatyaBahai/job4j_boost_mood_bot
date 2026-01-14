package ru.job4j.bmb.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanNameAware;
import ru.job4j.bmb.model.Achievement;
import ru.job4j.bmb.repository.AchievementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class
AchievementService {
    private final AchievementRepository achievementRepository;

    public List<Achievement> findAllByUserId(Long userId) {
        return achievementRepository.findAllByUserId(userId);
    }
}

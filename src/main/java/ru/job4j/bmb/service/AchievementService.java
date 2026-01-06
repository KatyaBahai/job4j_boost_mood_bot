package ru.job4j.bmb.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanNameAware;
import ru.job4j.bmb.repository.AchievementRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class
AchievementService implements BeanNameAware {
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        System.out.println("The bean is going through init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("The bean is going to be destroyed now.");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("AchievementService Bean name: " + name);
    }
}

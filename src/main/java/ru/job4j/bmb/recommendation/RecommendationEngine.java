package ru.job4j.bmb.recommendation;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecommendationEngine {
   // private final ContentProvider contentProvider;

    @PostConstruct
    public void init() {
        System.out.println("The bean is going through init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("The bean is going to be destroyed now.");
    }
}

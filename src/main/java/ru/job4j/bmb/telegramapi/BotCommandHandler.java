package ru.job4j.bmb.telegramapi;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import ru.job4j.bmb.content.Content;
import org.springframework.stereotype.Service;

@Service
public class BotCommandHandler {
    void receive(Content content) {
        System.out.println(content);
    }

    @PostConstruct
    public void init() {
        System.out.println("The bean is going through init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("The bean is going to be destroyed now.");
    }
}

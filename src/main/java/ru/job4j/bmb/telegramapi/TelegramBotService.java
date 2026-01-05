package ru.job4j.bmb.telegramapi;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import ru.job4j.bmb.content.Content;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelegramBotService {
    private final BotCommandHandler handler;

    public void receive(Content content) {
        handler.receive(content);
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

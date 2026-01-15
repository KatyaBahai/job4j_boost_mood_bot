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
}

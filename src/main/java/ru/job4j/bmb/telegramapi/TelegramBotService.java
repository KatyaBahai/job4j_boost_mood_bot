package ru.job4j.bmb.telegramapi;

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
}

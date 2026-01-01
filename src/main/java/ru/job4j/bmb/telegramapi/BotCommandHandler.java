package ru.job4j.bmb.telegramapi;

import ru.job4j.bmb.content.Content;
import org.springframework.stereotype.Service;

@Service
public class BotCommandHandler {
    void receive(Content content) {
        System.out.println(content);
    }
}

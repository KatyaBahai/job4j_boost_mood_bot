package ru.job4j.bmb.telegramapi;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.content.SendContent;
import ru.job4j.bmb.condition.OnFakeTgModeCondition;

@Service
@Conditional(OnFakeTgModeCondition.class)
public class FakeTelegramBotService extends TelegramLongPollingBot implements SendContent {
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Action performed on Update received");
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public void send(Content content) {
        System.out.println("Content sent");
    }
}

package ru.job4j.bmb.telegramapi;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.bmb.content.Content;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.SendContent;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.job4j.bmb.exception.SendContentException;

@Service
@AllArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot implements SendContent {
    private final BotCommandHandler handler;
    private final String botName;

    public TelegramBotService(@Value("${telegram.bot.name}") String botName,
                              @Value("${telegram.bot.token}") String botToken,
                              BotCommandHandler handler) {
        super(botToken);
        this.handler = handler;
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handler.handleCallback(update.getCallbackQuery())
                    .ifPresent(this::send);
        } else if (update.hasMessage() && update.getMessage().getText() != null) {
            handler.commands(update.getMessage())
                    .ifPresent(this::send);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void send(Content content) {
        try {
            if (content.getAudio() != null) {
                SendAudio sendAudio = SendAudio.builder()
                        .audio(content.getAudio())
                        .chatId(content.getChatId())
                        .caption(content.getText())
                        .build();
                execute(sendAudio);
            } else if (content.getPhoto() != null) {
                SendPhoto sendPhoto = SendPhoto.builder()
                        .chatId(content.getChatId())
                        .photo(content.getPhoto())
                        .caption(content.getText())
                        .build();
                execute(sendPhoto);
            } else if (content.getText() != null) {
                SendMessage sendMessage = SendMessage.builder()
                        .chatId(content.getChatId())
                        .text(content.getText())
                        .replyMarkup(content.getMarkup())
                        .build();
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new SendContentException("Could not provide content for your request, try again later", e);
        }
    }

    private void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        send(message);
    }
}

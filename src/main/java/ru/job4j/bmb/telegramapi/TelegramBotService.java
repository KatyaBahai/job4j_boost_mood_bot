package ru.job4j.bmb.telegramapi;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.bmb.condition.OnRealTgModeCondition;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.content.SendContent;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.job4j.bmb.exception.SendContentException;

@Component
@NoArgsConstructor
@Conditional(OnRealTgModeCondition.class)
public class TelegramBotService extends TelegramLongPollingBot implements SendContent {
    private BotCommandHandler handler;
    private String botName;

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotService.class);

    @Autowired
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
            CallbackQuery cb = update.getCallbackQuery();
            LOG.info("✅ CALLBACK: id={}, data='{}', chatId={}",
                    cb.getId(), cb.getData(), cb.getMessage().getChatId());
            handler.handleButtonCallback(update.getCallbackQuery()).ifPresent(this::send);
            handler.handleCallback(update.getCallbackQuery()).ifPresent(this::send);
            try {
                AnswerCallbackQuery acq = AnswerCallbackQuery.builder()
                        .callbackQueryId(update.getCallbackQuery().getId())
                        .build();
                execute(acq);
            } catch (TelegramApiException e) {
                LOG.error("Failed to answer callback", e);
            }
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
            LOG.error("There's something wrong with the send content method, no content could be provided.", e);
            throw new SendContentException("Could not provide content for your request, try again later", e);
        }
    }

    private void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Could not execute the given message: {}", message.getText(), e);
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        send(message);
    }
}

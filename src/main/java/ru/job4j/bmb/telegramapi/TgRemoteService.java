package ru.job4j.bmb.telegramapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.model.MoodContent;
import ru.job4j.bmb.repository.MoodContentRepository;
import ru.job4j.bmb.repository.MoodRepository;
import ru.job4j.bmb.repository.UserRepository;
import ru.job4j.bmb.model.User;

import java.util.*;

@Service
public class TgRemoteService extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final UserRepository userRepository;
    private final MoodContentRepository moodContentRepository;
    private final MoodRepository moodRepository;
    private final TgUI tgUi;

    public TgRemoteService(@Value("${telegram.bot.name}") String botName,
                           @Value("${telegram.bot.token}") String botToken,
                           UserRepository userRepository, MoodContentRepository moodContentRepository, MoodRepository moodRepository, TgUI tgUi) {
        this.botName = botName;
        this.botToken = botToken;
        this.userRepository = userRepository;
        this.moodContentRepository = moodContentRepository;
        this.moodRepository = moodRepository;
        this.tgUi = tgUi;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public String getResponse(String mood) {
        Optional<Mood> moodOpt = moodRepository.findByText(mood);
        if (moodOpt.isEmpty()) {
            return """
                Sorry, I didn't understand you,
                but everything is going to be just fine!
                """;
        }
        Long moodId = moodOpt.get().getId();

        Optional<MoodContent> moodContent = moodContentRepository.findByMoodId(moodId);
        if (moodContent.isEmpty()) {
            return """
                        Sorry, I didn't understand you,
                        but everything is going to be just fine!
                        """;
        }
        return moodContent.get().getText();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            var data = update.getCallbackQuery().getData();
            var chatId = update.getCallbackQuery().getMessage().getChatId();
            send(new SendMessage(String.valueOf(chatId), getResponse(data)));
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage();
            if ("/start".equals(message.getText())) {
                long chatId = message.getChatId();
                var user = new User();
                user.setClientId(message.getFrom().getId());
                user.setChatId(chatId);
                userRepository.save(user);
                send(sendButtons(chatId));
            }
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

    public SendMessage sendButtons(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Как настроение сегодня?");
        message.setReplyMarkup(tgUi.buildButtons());
        return message;
    }
}

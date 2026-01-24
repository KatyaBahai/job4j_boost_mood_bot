package ru.job4j.bmb.telegramapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.bmb.model.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.job4j.bmb.content.Content;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.repository.MoodContentRepository;
import ru.job4j.bmb.repository.UserRepository;
import ru.job4j.bmb.service.MoodService;

import java.util.Optional;

@Service
public class BotCommandHandler {

    private final MoodContentRepository moodContentRepository;
    private final UserRepository userRepository;
    private final MoodService moodService;
    private final TgUI tgUI;

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);

    public BotCommandHandler(MoodContentRepository moodContentRepository, UserRepository userRepository,
                             MoodService moodService,
                             TgUI tgUI) {
        this.moodContentRepository = moodContentRepository;
        this.userRepository = userRepository;
        this.moodService = moodService;
        this.tgUI = tgUI;
    }

    private Optional<Content> respondToNewUser(long clientId, long chatId) {
            return Optional.of(Content.builder()
                    .chatId(chatId)
                    .text("Sorry, no user registered yet, please, type /start.")
                    .build());
    }

    Optional<Content> commands(Message message) {
        long chatId = message.getChatId();
        long clientId = message.getFrom().getId();

        return switch (message.getText()) {
            case "/start" -> handleStartCommand(chatId, clientId);
            case "/week_mood_log" -> userRepository.existsByClientId(clientId)
                    ? moodService.weekMoodLogCommand(chatId, clientId)
                    : respondToNewUser(chatId, clientId);
            case "/month_mood_log" -> userRepository.existsByClientId(clientId)
                    ? moodService.monthMoodLogCommand(chatId, clientId)
                    : respondToNewUser(chatId, clientId);
            case "/award" -> userRepository.existsByClientId(clientId)
                    ? moodService.awards(chatId, clientId)
                    : respondToNewUser(chatId, clientId);
            default -> Optional.empty();
        };
    }

    Optional<Content> handleCallback(CallbackQuery callback) {
        System.out.println("🔍 Processing callback data");
        var moodId = Long.valueOf(callback.getData());
        var user = userRepository.findByClientId(callback.getFrom().getId());
        System.out.printf("🔍 User is %s%n", user.get().getClientId());
        return user.map(value -> moodService.chooseMood(value, moodId));
    }

    Optional<Content> handleButtonCallback(CallbackQuery callback) {
        var moodId = Long.valueOf(callback.getData());
        var chatId = callback.getMessage().getChatId();
        var user = userRepository.findByClientId(callback.getFrom().getId());
        if (user.isEmpty()) {
            return Optional.of(Content.builder()
                    .chatId(chatId)
                    .text("Sorry, you have to register first, type /start")
                    .build());
        }
        var moodContentOpt = moodContentRepository.findByMoodId(moodId);
        return moodContentOpt.map(moodContent -> Content.builder()
                .chatId(chatId)
                .text(moodContent.getText())
                .build()).or(() -> Optional.of(Content.builder()
                .chatId(chatId)
                .text("Hey, I am not myself today, could you try another mood button?")
                .build()));
    }

    @Transactional
    private Optional<Content> handleStartCommand(long chatId, Long clientId) {
        Optional<User> existingUser = userRepository.findByClientId(clientId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            return createContent(user);
        }
        User newUser = new User();
        newUser.setClientId(clientId);
        newUser.setChatId(chatId);
        userRepository.save(newUser);
        return createContent(newUser);
    }

    private Optional<Content> createContent(User user) {
        var content = new Content(user.getChatId());
        content.setText("Как настроение?");
        content.setMarkup(tgUI.buildButtons());
        return Optional.of(content);
    }

}

package ru.job4j.bmb.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.event.UserEvent;
import ru.job4j.bmb.model.Achievement;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.model.User;
import ru.job4j.bmb.recommendation.RecommendationEngine;
import ru.job4j.bmb.repository.MoodLogRepository;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.repository.MoodRepository;
import ru.job4j.bmb.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MoodService {
    private final RecommendationEngine recommendationEngine;
    private final MoodLogRepository moodLogRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;
    private final MoodRepository moodRepository;
    private final ApplicationEventPublisher publisher;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodLogRepository moodLogRepository,
                       RecommendationEngine recommendationEngine,
                       UserRepository userRepository,
                       AchievementService achievementService,
                       MoodRepository moodRepository, ApplicationEventPublisher publisher) {
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.userRepository = userRepository;
        this.achievementService = achievementService;
        this.moodRepository = moodRepository;
        this.publisher = publisher;
    }

    private void saveMoodLog(User user, Long moodId) {
        Mood mood = moodRepository.findById(moodId).orElseThrow(
                () -> new IllegalArgumentException("There's no mood with the given id"));

        MoodLog moodLog = MoodLog.builder()
                .mood(mood)
                .user(user)
                .creationDate(System.currentTimeMillis())
                .build();
        moodLogRepository.save(moodLog);
    }

    public Content chooseMood(User user, Long moodId) {
        saveMoodLog(user, moodId);
        publisher.publishEvent(new UserEvent(this, user));
        return recommendationEngine.recommendFor(user.getChatId(), moodId);
    }

    public Optional<Content> weekMoodLogCommand(long chatId, Long clientId) {
        return getMoodLogs(chatId, clientId, Instant.now().minus(7, ChronoUnit.DAYS),
                "Your weekly mood log");
    }

    public Optional<Content> monthMoodLogCommand(long chatId, Long clientId) {
        return getMoodLogs(chatId, clientId, Instant.now().minus(30, ChronoUnit.DAYS),
                "Your monthly mood log");
    }

    public Optional<Content> getMoodLogs(long chatId, Long clientId, Instant startingFrom, String logTitle) {
        User user = userRepository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + clientId));

        List<MoodLog> moodLogs = moodLogRepository.findAllByUserIdAndCreationDateGreaterThan(user.getId(), startingFrom.toEpochMilli());

        var content = Content.builder()
                .text(formatMoodLogs(moodLogs, logTitle))
                .chatId(chatId)
                .build();
        return Optional.of(content);
    }

    private String formatMoodLogs(List<MoodLog> logs, String title) {
        if (logs.isEmpty()) {
            return title + ":\nNo mood logs found.";
        }
        var sb = new StringBuilder(title + ":\n");
        logs.forEach(log -> {
            String formattedDate = formatter.format(Instant.ofEpochMilli(log.getCreationDate()));
            sb.append(formattedDate).append(": ").append(log.getMood().getText()).append("\n");
        });
        return sb.toString();
    }

    private String formatAchievements(List<Achievement> achievements, String title) {
        if (achievements.isEmpty()) {
            return title + ":\nYou don't have good mood achievements yet. Never late to get started!";
        }
        var sb = new StringBuilder(title + ":\n");
        achievements.forEach(achievement -> {
            String formattedDate = formatter.format(Instant.ofEpochMilli(achievement.getCreationDate()));
            sb.append(formattedDate).append(": ").append(achievement.getAward().getTitle()).append("\n");
        });
        return sb.toString();
    }

    public Optional<Content> awards(long chatId, Long clientId) {
        User user = userRepository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + clientId));

        List<Achievement> achievements = achievementService.findAllByUserId(user.getId());

        var content = Content.builder()
                .text(formatAchievements(achievements, "Here's your list of awards for good mood"))
                .chatId(chatId)
                .build();
        return Optional.of(content);
    }
}
